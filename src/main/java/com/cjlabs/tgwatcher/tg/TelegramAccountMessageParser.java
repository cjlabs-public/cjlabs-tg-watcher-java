package com.cjlabs.tgwatcher.tg;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TelegramAccountMessageParser {

    private static final Pattern ACCOUNT_PATTERN = Pattern.compile("\\b\\d+-\\d+-\\d+-\\d+\\b");
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\b0(?:\\s*\\d){7,10}\\b");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?i)\\b(?:password|pass|pwd|pw)\\b\\s*[:：]?\\s*([A-Za-z0-9@#$%^&*._-]+)");
    private static final Pattern NAME_PATTERN = Pattern.compile("(?i)\\bname\\b\\s*[:：]?\\s*\\(?\\s*([^\\r\\n()]+?)\\s*\\)?\\s*$");
    private static final Pattern BANK_PATTERN = Pattern.compile("(?i)\\b(PPCBank|ABA|ACLEDA|Wing|TrueMoney|Canadia|Maybank|Vattanac|Prince)\\b");

    public List<ParsedAccountMessage> parse(String content) {
        if (content == null || content.isBlank()) {
            return List.of();
        }

        List<ParsedAccountMessage> records = new ArrayList<>();
        for (String block : splitBlocks(content)) {
            ParsedAccountMessage record = parseBlock(block);
            if (record.accountNo() != null || record.phone() != null || record.accountName() != null) {
                records.add(record);
            }
        }
        return records;
    }

    private ParsedAccountMessage parseBlock(String block) {
        String phone = null;
        String account = null;
        String name = null;
        String password = null;
        List<String> remarks = new ArrayList<>();

        String[] lines = block.replace("\r\n", "\n").replace('\r', '\n').split("\n");
        for (String sourceLine : lines) {
            String line = normalizeLine(sourceLine);
            line = stripLeadingOrdinal(line);
            if (line.isBlank()) {
                continue;
            }

            Matcher accountMatcher = ACCOUNT_PATTERN.matcher(line);
            if (accountMatcher.find()) {
                account = accountMatcher.group();
                continue;
            }

            String passwordValue = matchFirst(PASSWORD_PATTERN, line);
            if (passwordValue != null) {
                password = cleanupValue(passwordValue);
                continue;
            }

            if (startsWithAny(line, "phone number", "phone", "tel", "mobile")) {
                phone = normalizePhone(cleanupValue(removeLabel(line)));
                if (phone.isBlank()) {
                    phone = normalizePhone(matchText(PHONE_PATTERN, line));
                }
                continue;
            }

            if (startsWithAny(line, "name")) {
                Matcher nameMatcher = NAME_PATTERN.matcher(line);
                name = nameMatcher.find() ? cleanupValue(nameMatcher.group(1)) : cleanupValue(removeLabel(line));
                continue;
            }

            if (startsWithAny(line, "account", "acc")) {
                String value = cleanupValue(removeLabel(line));
                Matcher valueAccountMatcher = ACCOUNT_PATTERN.matcher(value);
                if (valueAccountMatcher.find()) {
                    account = valueAccountMatcher.group();
                }
                continue;
            }

            String linePhone = matchText(PHONE_PATTERN, line);
            if (linePhone != null && phone == null) {
                phone = normalizePhone(linePhone);
                continue;
            }

            if (line.matches("^\\(?\\s*\\d+\\s*\\)?$")) {
                remarks.add(cleanupValue(line));
                continue;
            }

            remarks.add(line);
        }

        return new ParsedAccountMessage(
                blankToNull(phone),
                blankToNull(account),
                blankToNull(name),
                blankToNull(password),
                blankToNull(String.join(" ", remarks))
        );
    }

    private List<String> splitBlocks(String content) {
        String normalized = content.replace("\r\n", "\n").replace('\r', '\n').strip();
        String[] parts = normalized.split("\\n\\s*\\n+");
        List<String> blocks = new ArrayList<>();

        for (String part : parts) {
            blocks.addAll(splitContinuousRecords(part.strip()));
        }
        return blocks;
    }

    private List<String> splitContinuousRecords(String text) {
        List<String> records = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean hasAccount = false;
        boolean hasPhone = false;
        boolean hasName = false;

        for (String sourceLine : text.split("\n")) {
            String line = normalizeLine(sourceLine);
            if (line.isBlank()) {
                continue;
            }

            boolean newRecordLine = isAccountRecordStartLine(line) && hasAccount;
            if (newRecordLine && !current.isEmpty()) {
                records.add(current.toString());
                current.setLength(0);
                hasAccount = false;
                hasPhone = false;
                hasName = false;
            }

            if (!current.isEmpty()) {
                current.append('\n');
            }
            current.append(line);

            hasAccount = hasAccount || ACCOUNT_PATTERN.matcher(line).find();
            hasPhone = hasPhone || PHONE_PATTERN.matcher(line).find();
            hasName = hasName || startsWithAny(line, "name");
        }

        if (!current.isEmpty()) {
            records.add(current.toString());
        }
        return records;
    }

    private boolean isAccountRecordStartLine(String line) {
        String normalized = line.strip().replaceFirst("^\\d+\\s*[/.)]\\s*", "");
        String lower = normalized.toLowerCase(Locale.ROOT);
        return lower.startsWith("acc")
                || lower.startsWith("account");
    }

    private String normalizeLine(String line) {
        return line == null ? "" : line.replaceAll("\\s+", " ").trim();
    }

    private String stripLeadingOrdinal(String line) {
        return line == null ? "" : line.replaceFirst("^\\d+\\s*[/.)]\\s*", "").trim();
    }

    private boolean startsWithAny(String line, String... prefixes) {
        String lower = line.toLowerCase(Locale.ROOT);
        for (String prefix : prefixes) {
            if (lower.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private String removeLabel(String line) {
        return line.replaceFirst("(?i)^[A-Za-z ]+\\s*[:：]?\\s*", "");
    }

    private boolean looksLikeAccountLabel(String text) {
        return text.toLowerCase(Locale.ROOT).contains("account") || text.toLowerCase(Locale.ROOT).contains("acc");
    }

    private String findBank(String line) {
        Matcher matcher = BANK_PATTERN.matcher(line);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String matchFirst(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group(1) : null;
    }

    private String matchText(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? matcher.group() : null;
    }

    private String cleanupValue(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("^[:：]+", "")
                .replaceAll("(?i)\\b(PPCBank|ABA|ACLEDA|Wing|TrueMoney|Canadia|Maybank|Vattanac|Prince)\\b", "")
                .replaceAll("^\\(+|[\\),，]+$", "")
                .trim();
    }

    private String normalizePhone(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("\\s+", "");
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
