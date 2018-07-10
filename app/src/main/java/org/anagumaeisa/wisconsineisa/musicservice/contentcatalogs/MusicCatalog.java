package org.anagumaeisa.wisconsineisa.musicservice.contentcatalogs;

import org.anagumaeisa.wisconsineisa.R;

import java.util.ArrayList;

public class MusicCatalog {
    private static ArrayList<Song> musicCatalog = new ArrayList<>();

    static {
        musicCatalog.add(new Song(
                "agechikuten",
                "Agechikuten",
                "揚作田",
                "Hidekatsu",
                "日出克",
                "Sōsaku Geidan Rekiosu",
                "創作芸団レキオス",
                "Okinawan Folk",
                147,
                "mp3/hidekatsu/sosaku_geidan_rekiosu/agechikuten.mp3",
                R.drawable.sosaku_geidan_rekiosu,
                "sosaku_geidan_rekiosu.jpg"
        ));

        musicCatalog.add(new Song(
                "asadoya_yunta",
                "Asadoya Yunta",
                "安里屋ユンタ",
                "",
                "",
                "",
                "",
                "Okinawan Folk",
                202,
                "mp3/no_artist/no_album/asadoya_yunta.mp3",
                R.drawable.anaguma_eisa,
                "anaguma_eisa.png"
        ));

        musicCatalog.add(new Song(
                "asadoya_yunta_karaoke",
                "Asadoya Yunta <Karaoke>",
                "安里屋ゆんた〈カラオケ〉",
                "Aiko Shakanoobi, Pine Girl",
                "饒辺愛子・パイン娘",
                "沖縄島唄ベスト集",
                "Okinawa Shimauta Best Collection",
                "Okinawan Folk",
                165,
                "mp3/",
                R.drawable.okinawa_shimauta_best_collection,
                "okinawa_shimauta_best_collection.jpg"
        ));

        musicCatalog.add(new Song(
                "beni_hachi",
                "Beni Hachi",
                "紅八",
                "Hidekatsu",
                "日出克",
                "Sōsaku Geidan Rekiosu",
                "創作芸団レキオス",
                "Okinawan Folk",
                252,
                "mp3/hidekatsu/sosaku_geidan_rekiosu/beni_hachi.mp3",
                R.drawable.sosaku_geidan_rekiosu,
                "sosaku_geidan_rekiosu.jpg"
        ));

        musicCatalog.add(new Song(
                "do-hya-do",
                "Do-Hya-Do",
                "ドーヒャードー",
                "Hidekatsu",
                "日出克",
                "Sōsaku Geidan Rekiosu",
                "創作芸団レキオス",
                "Okinawan Folk",
                203,
                "mp3/hidekatsu/sosaku_geidan_rekiosu/do_hya_do.mp3",
                R.drawable.sosaku_geidan_rekiosu,
                "sosaku_geidan_rekiosu.jpg"
        ));

        musicCatalog.add(new Song(
                "kaze_no_yubito",
                "Kaze no Yūbito",
                "",
                "Hidekatsu",
                "日出克",
                "Mapirōma",
                "マピローマ",
                "Okinawan Folk",
                240,
                "mp3/hidekatsu/mapiroma/kaze_no_yubito.mp3",
                R.drawable.mapiroma,
                "mapiroma.jpg"
        ));
    }

    public static Song getSong(int i) {
        return musicCatalog.get(i);
    }

    public static int size() {
        return musicCatalog.size();
    }

    public static String[] getAllSongsArray() {
        String[] songArray = new String[musicCatalog.size()];

        for (int i = 0; i < musicCatalog.size(); i++) {
            songArray[i] = musicCatalog.get(i).getTitle();
        }

        return songArray;
    }
}
