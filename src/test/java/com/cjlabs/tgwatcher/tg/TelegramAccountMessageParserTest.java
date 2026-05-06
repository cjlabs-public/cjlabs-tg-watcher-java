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

        assertThat(records.get(2).remark()).isNull();
        assertThat(records.get(2).accountNo()).isEqualTo("1-120-00299067-9");

        assertThat(records.get(4).remark()).isNull();
        assertThat(records.get(4).accountNo()).isEqualTo("1-120-00299305-5");

        assertThat(records.get(5).remark()).isEqualTo("2");
    }

    @Test
    void parsesSpecialFormats() {
        String content = """
                1/ Account: PPCBank
                PPCBank 1-120-00298880-8
                Account: PPCBank
                1-120-00299043-1

                Phone: 0888067538
                Account:1-120-00297275-5
                Name:NHOEK CHANDARAVOTH
                PW:757001

                PHONE NUMBER :0967400106
                NAME: SOT SOKVISAL
                Bank number : 1-120-00299469-2
                PW:111222

                Phone number: 096 3013 734
                Acc: 1-120-00302353-3
                Name : SENG MEHEANG
                Password: 121212
                (9)

                Name: (ROTH PUNLUE)，
                Acc: 1-120-00290000-1
                """;

        List<ParsedAccountMessage> records = parser.parse(content);

        assertThat(records).hasSize(6);
        assertThat(records.get(0).accountNo()).isEqualTo("1-120-00298880-8");
        assertThat(records.get(0).remark()).isNull();
        assertThat(records.get(1).accountNo()).isEqualTo("1-120-00299043-1");
        assertThat(records.get(1).remark()).isNull();

        assertThat(records.get(2).phone()).isEqualTo("0888067538");
        assertThat(records.get(2).accountNo()).isEqualTo("1-120-00297275-5");
        assertThat(records.get(2).accountName()).isEqualTo("NHOEK CHANDARAVOTH");
        assertThat(records.get(2).passwordPlain()).isEqualTo("757001");

        assertThat(records.get(3).phone()).isEqualTo("0967400106");
        assertThat(records.get(3).accountNo()).isEqualTo("1-120-00299469-2");
        assertThat(records.get(3).accountName()).isEqualTo("SOT SOKVISAL");
        assertThat(records.get(3).passwordPlain()).isEqualTo("111222");

        assertThat(records.get(4).phone()).isEqualTo("0963013734");
        assertThat(records.get(4).accountNo()).isEqualTo("1-120-00302353-3");
        assertThat(records.get(4).remark()).isEqualTo("9");

        assertThat(records.get(5).accountName()).isEqualTo("ROTH PUNLUE");
    }
}
