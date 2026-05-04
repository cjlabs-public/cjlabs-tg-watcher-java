package com.cjlabs.tgwatcher.tg;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TelegramAccountMessageParserTest {

    private final TelegramAccountMessageParser parser = new TelegramAccountMessageParser();

    @Test
    void parsesMultiRecordAccountMessage() {
        String content = """
                Phone: 086656118
                Account:  1-120-00297860-5
                Name:(KHATT CHANVEASNA)
                Password: 600000

                Acc: 1-120-00298979-3
                Name: SUN KHOAN
                Phone : 0963381321
                Password: 336699

                Phone: 090383056
                Account: PPCBank 1-120-00299067-9
                Name: (SOUN VICHEKA)
                Password: 111111

                Phone number: 095338519
                Acc: 1-120-00298257-9
                Name: KHIVE SOTHEARA
                Password: 123456

                Phone: 0967971640
                Account: PPCBank
                1-120-00299305-5
                Name :(HUN SREYPOV)
                Password: 222222

                Phone number: 0765786737
                Acc: 1-120-00301733-9
                Name : HENG SOPRCAKTRA
                Password: 555888
                (2)
                """;

        List<ParsedAccountMessage> records = parser.parse(content);

        assertThat(records).hasSize(6);
        assertThat(records.get(0).phone()).isEqualTo("086656118");
        assertThat(records.get(0).accountNo()).isEqualTo("1-120-00297860-5");
        assertThat(records.get(0).accountName()).isEqualTo("KHATT CHANVEASNA");
        assertThat(records.get(0).passwordPlain()).isEqualTo("600000");

        assertThat(records.get(2).remark()).isEqualTo("PPCBank");
        assertThat(records.get(2).accountNo()).isEqualTo("1-120-00299067-9");

        assertThat(records.get(4).remark()).isEqualTo("PPCBank");
        assertThat(records.get(4).accountNo()).isEqualTo("1-120-00299305-5");

        assertThat(records.get(5).remark()).isEqualTo("2");
    }
}
