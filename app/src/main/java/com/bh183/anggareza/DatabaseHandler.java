package com.bh183.anggareza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_filmku";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_PRODUSER = "Produser";
    private final static String KEY_SINOPSIS = "Sinopsis";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;




    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " DATE, "
                + KEY_PRODUSER + " TEXT, " + KEY_SINOPSIS + " DATE, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_PRODUSER, dataFilm.getProduser());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_PRODUSER, dataFilm.getProduser());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_PRODUSER, dataFilm.getProduser());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());
        cv.put(KEY_LINK, dataFilm.getLink());


        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});

        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Date tempDate = new Date();
                try{
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db) {
        int idFilm = 0;
        Date tempDate = new Date();

        //film1
        try{
            tempDate = sdFormat.parse("28/02/2019 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }



        Film film1 = new Film(
                idFilm,
                "Dilan 1991",
                tempDate,
                storeImageFile(R.drawable.film1),
                "cover film dilan 1991(Wikipedia)",
                "Produser : Ody Mulya Hidayat",
                "22 Desember 1990, Dilan (Iqbaal Ramadhan) dan Milea (Vanesha Prescilla) resmi berpacaran. Ditengah kebahagiaan mereka Dilan terancam dikeluarkan dari sekolah akibat perkelahian dengan Anhar (Giulio Parengkuan). Dilan juga semakin sering berkelahi dan mendapatkan musuh. Milea khawatir dengan masa depan Dilan. Milea merasa berhak melarang Dilan terlibat dalam geng motor. Suatu ketika, Dilan dikeroyok oleh orang tak dikenal. Saat mengetahui siapa yang berbuat, Dilan merencanakan balas dendam. Milea akhirnya meminta Dilan berhenti dari geng motor atau hubungan mereka berakhir. Dilan, seorang panglima tempur dan ketua geng motor akan selalu terlibat masalah. Di tengah semua masalah itu, hadir Yugo (Jerome Kurnia), anak dari sepupu jauh ayah Milea yang baru pulang dari Belgia. Mereka sering menghabiskan waktu bersama. Yugo menyukai Milea, dan Milea hanya mencintai Dilan.",
                "https://id.wikipedia.org/wiki/Dilan_1991"
        );

        tambahFilm(film1, db);
        idFilm++;

        //film2
        try{
            tempDate = sdFormat.parse("20/12/2012 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "Habibie & Ainun",
                tempDate,
                storeImageFile(R.drawable.film2),
                "cover film Habibie & Ainun(Wikipedia)",
                "Produser : Dhamoo Punjabi, Manoj Punjabi",
                "Rudy Habibie seorang genius ahli pesawat terbang yang punya mimpi besar: berbakti kepada bangsa Indonesia dengan membuat pesawat terbang untuk menyatukan Indonesia. Sedangkan Ainun adalah seorang dokter muda cerdas yang dengan jalur karier terbuka lebar untuknya.\n" +
                        "\n" +
                        "Pada tahun 1962, dua kawan SMP ini bertemu lagi di Bandung. Habibie jatuh cinta seketika pada Ainun yang baginya semanis gula. Tapi Ainun, dia tak hanya jatuh cinta, dia iman pada visi dan mimpi Habibie. Mereka menikah dan terbang ke Jerman.\n" +
                        "\n" +
                        "Punya mimpi tak akan pernah mudah. Habibie dan Ainun tahu itu. Cinta mereka terbangun dalam perjalanan mewujudkan mimpi. Dinginnya salju Jerman, pengorbanan, rasa sakit, kesendirian serta godaan harta dan kuasa saat mereka kembali ke Indonesia mengiringi perjalanan dua hidup menjadi satu.\n" +
                        "\n" +
                        "Bagi Habibie, Ainun adalah segalanya. Ainun adalah mata untuk melihat hidupnya. Bagi Ainun, Habibie adalah segalanya, pengisi kasih dalam hidupnya. Namun setiap kisah mempunyai akhir, setiap mimpi mempunyai batas. ",
                "https://id.wikipedia.org/wiki/Habibie_%26_Ainun_(film)"
        );

        tambahFilm(film2, db);
        idFilm++;

        //film3
        try{
            tempDate = sdFormat.parse("17/03/2017 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "Beauty and the Beast",
                tempDate,
                storeImageFile(R.drawable.film3),
                "cover film Beauty and the Beast",
                "Produser : David Hoberman, Todd Lieberman",
                "Di Prancis pada era Rococo, seorang penyihir yang menyamar menjadi pengemis tiba di sebuah pesta dan menawarkan kepada tuan rumah, seorang pangeran berhati dingin, setangkai mawar sebagai ganti untuk tempat berteduh. Setelah ditolak oleh sang pangeran, ia mengutuknya menjadi makhluk buruk rupa dan para pelayannya menjadi perabot rumah tangga, serta menghapus seluruh isi istana dari memori orang-orang yang mereka sayangi. Ia memberikan sang pangeran mawar yang telah disihir dan memperingatkannya bahwa jika dia tidak bisa belajar mencintai seseorang dan balas dicintai sebelum kelopak terakhir gugur, dia dan para pelayannya tidak akan bisa menjadi manusia lagi selamanya.\n" +
                        "\n" +
                        "Beberapa tahun kemudian, di desa Villeneuve, Belle bermimpi akan petualangan dan menolak rayuan dari Gaston, seorang mantan tentara yang arogan. Ayah Belle, Maurice, tersesat di hutan dan pada akhirnya mencari perlindungan di kastil milik Beast, namun Beast menawannya karena memetik mawar tanpa ijin. Belle pergi mencarinya dan menemukannya terkurung di penjara kastil. Beast sepakat untuk mengijinkannya menggantikan Maurice.\n" +
                        "\n" +
                        "Belle berteman dengan para pelayan kastil, yang menjamunya dengan makan malam spektakuler. Saat dia berkeluyuran ke sayap barat yang terlarang dan menemukan mawarnya, Beast yang sangat marah mengusirnya dari kastil. Saat tiba di hutan, ia disudutkan oleh sekawanan serigala. Beast datang tepat waktu untuk menyelamatkannya namun terluka sebagai akibatnya. Belle merawatnya sampai sembuh dan seiring itu mulai menjalin pertemanan dengan Beast. Beast menunjukkan ke Belle hadiah dari sang penyihir, sebuah buku yang bisa membawa pembacanya ke tempat manapun. Belle menggunakannya untuk mengunjungi rumah masa kecilnya di Paris, di mana dia menemukan masker yang digunakan dokter saat wabah. Dia menyadari bahwa ayahnya terpaksa membawanya pergi meninggalkan ibunya yang sekarat untuk mencegahnya ikut terkena wabah.\n" +
                        "\n" +
                        "Di Villeneuve, Gaston sepakat untuk membantu Maurice menyelamatkan Belle dengan harapan Belle akan mau menikah dengannya. Saat Maurice mengetahui niatnya dan menolaknya, Gaston meninggalkannya di tengah hutan untuk dimangsa serigala. Maurice ditolong oleh seorang pengemis, Agathe, namun saat ia menceritakan apa yang terjadi ke penduduk desa, Gaston meyakinkan mereka untuk mengirimkannya ke rumah sakit jiwa.\n" +
                        "\n" +
                        "Setelah berdansa dengan Beast, Belle mengetahui keadaan ayahnya lewat cermin ajaib. Beast membebaskannya agar bisa menyelamatkan Maurice, dan memberikan cerminnya kepada Belle agar dia selalu bisa mengingatnya. Di Villeneuve, Belle membuktikan kewarasan Maurice dengan memperlihatkan Beast lewat cermin ke para penduduk desa. Sadar bahwa Belle mencintai Beast, Gaston mengurungnya bersama ayahnya dan mengerahan penduduk desa menuju kastil untuk membunuh Beast. Maurice dan Belle meloloskan diri dan Belle segera bergegas kembali ke kastil.\n" +
                        "\n" +
                        "Saat pertarungan berlangsung, Gaston meninggalkan Le Fou, yang akhirnya beralih pihak ke para pelayan kastil untuk melawan penduduk desa. Gaston menyerang Beast yang terlalu sedih untuk balik melawan, namun akhirnya mendapat kemauan lagi setelah melihat Belle telah kembali. Ia memutuskan melepaskan Gaston sebelum bersatu kembali dengan Belle. Tanpa disangka, Gaston menembak Beast dari sebuah jembatan yang kemudian ambruk dan membunuhnya. Beast meninggal tepat saat kelopak terakhir gugur dan para pelayan berubah menjadi benda mati. Ketika Belle mengungkapkan cintanya ke Beast, Agathe menampakkan dirinya sebagai penyihir dan membatalkan kutukannya, menghidupkan kembali Beast dan para pelayannya menjadi wujud asli mereka serta mengembalikan memori para penduduk desa. Sang pangeran dan Belle menggelar pesta perayaan, di mana mereka berdansa dengan bahagia.",
                "https://id.wikipedia.org/wiki/Beauty_and_the_Beast_(film_2017)"
        );

        tambahFilm(film3, db);
        idFilm++;

        //film4
        try{
            tempDate = sdFormat.parse("24/04/2019 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "Avengers: Endgame",
                tempDate,
                storeImageFile(R.drawable.film4),
                "cover film Avengers: Endgame",
                "Produser : Kevin Feige",
                "Dua puluh tiga hari setelah Thanos menggunakan Infinity Gauntlet untuk menghancurkan setengah dari seluruh kehidupan di alam semesta, Carol Danvers menyelamatkan Nebula dan Tony Stark, yang terdampar di luar angkasa setelah pertarungan mereka dengan Thanos. Mereka bergabung dengan Natasha Romanoff, Bruce Banner, Steve Rogers, Rocket, Thor, Pepper Potts, dan James Rhodes di Bumi. Kelompok itu menemukan Thanos disebuah planet terpencil dan berencana untuk merebut dan menggunakan batu Infinity dan membalikkan tindakannya, tetapi Thanos telah menghancurkan batu tersebut untuk mencegah digunakan kembali. Thor yang marah akhirnya memenggal Thanos.\n" +
                        "\n" +
                        "Lima tahun kemudian, Scott Lang keluar dari alam kuantum. Ia pergi ke markas Avengers, dimana ia menjelaskan kepada Romanoff dan Rogers bahwa ia hanya melalui lima jam di alam kuantum, bukan lima tahun. dan ia berteori bahwa alam kuantum dapat memungkinkan mereka melakukan perjalanan waktu ke masa lalu, Ketiganya meminta Stark — yang sekarang membesarkan seorang putri, Morgan, bersama Pepper — agar mereka mengambil batu Infinity dari masa lalu dan menggunakannya untuk mengembalikan tindakan Thanos di masa sekarang. Stark menolak gagasan itu karena takut kehilangan Morgan, tetapi mengalah setelah merenungkan Peter Parker yang hancur. Stark bekerja bersama Banner, yang telah menyatukan kepintarannya dan kekuatan Hulk, untuk mendesain perangkat untuk melakukan perjalanan waktu. Banner memperingatkan bahwa mengubah masa lalu tidak mengubah masa kini dan perubahan sedikitpun dapat menciptakan realita yang berbeda. Ia dan Rocket pergi ke rumah baru pengungsi Asgard di Norwegia - New Asgard - untuk merekrut Thor, yang kini menjadi pemabuk gemuk, putus asa karena kegagalannya dalam menghentikan Thanos. Di Tokyo, Romanoff merekrut Clint Barton, yang kini menjadi seorang pejuang jalanan setelah keluarganya menghilang.\n" +
                        "\n" +
                        "Banner, Lang, Rogers, dan Stark pergi ke Kota New York di tahun 2012. Banner mengunjungi Sanctum Sanctorum dan meyakinkan Ancient One untuk memberinya Batu Waktu. Rogers berhasil mengambil Batu Pikiran, namun Stark dan Lang tidak sengaja membiarkan Loki dari tahun 2012 untuk kabur dengan Batu Angkasa. Rogers dan Stark pergi ke markas S.H.I.E.L.D. di tahun 1970, dimana Stark mengambil versi awal dari Batu Angkasa dan bertemu dengan Howard Stark muda saat mengambilnya, sedangkan Rogers mengambil beberapa Partikel Pym dari Hank Pym untuk kembali ke masa kini. Rocket dan Thor pergi ke Asgard di tahun 2013, mengekstrak Batu Realita dari Jane Foster dan mengambil palu Thor, Mjolnir. Nebula dan Rhodes pergi ke Morag di tahun 2014 dan mencuri Batu Kekuatan sebelum Peter Quill dapat mencurinya. Rhodes kembali ke masa kini dengan Batu Kekuatan, namun Nebula tidak sadarkan diri ketika implan cyberneticnya berhubungan dengan dirinya dari masa lalu. Melalui hubungan ini, Thanos 2014 mempelajari mengenai keberhasilannya di masa depan dan keinginan Avengers untuk membalikkannya. Thanos menangkap Nebula 2023 dan mengirim Nebula 2014 ke masa kini untuk menggantikannya. Barton dan Romanoff pergi ke Vormir, dimana sang penjaga Batu Jiwa, Red Skull, memberitahu mereka bahwa batu tersebut hanya dapat diambil dengan mengorbankan seseorang yang mereka cintai. Romanoff mengorbankan dirinya, dan Barton mengambil Batu Jiwa.\n" +
                        "\n" +
                        "Kembali ke masa kini, para Avengers memasangkan batu-batu tersebut ke sarung tangan yang dibuat oleh Stark, dimana Banner menggunakannya untuk mengembalikan semua yang Thanos hilangkan. Nebula 2014 menggunakan mesin waktu untuk membawa Thanos 2014 dan kapal perangnya ke masa kini, dan menyerang markas Avengers, berencana untuk menghancurkan dan membangun kembali alam semesta dengan Batu Infinity. Nebula 2023 meyakinkan Gamora 2014 untuk mengkhianati Thanos dan membunuh Nebula 2014. Stark, Rogers, dan Thor bertarung melawan Thanos namun kewalahan. Thanos memanggil pasukannya untuk menghancurkan Bumi, namun Stephen Strange yang kembali datang dengan penyihir lainnya, para Avengers, dan Guardians of the Galaxy yang hilang, pasukan Wakanda dan Asgard, dan para Ravagers untuk melawan Thanos dan pasukannya bersama Danvers, yang menghancurkan kapal Thanos ketika ia tiba. setelah mengalahkan para pahlawan, Thanos merebut sarung tangan tersebut, namun Stark mencuri batunya dan menggunakannya untuk menghilangkan Thanos dan Pasukannya, lalu mati karena energi yang dikeluarkan oleh batu tersebut.\n" +
                        "\n" +
                        "Setelah pemakaman Stark, Thor menunjuk Valkyrie sebagai penguasa Asgard Baru dan bergabung dengan Guardians of the Galaxy, dimana Quill mencari Gamora 2014. Rogers mengembalikan Batu Infinity dan Mjolnir ke waktunya masing-masing dan menetap di masa lalu untuk hidup bersama Peggy Carter. Di masa kini, Rogers yang sudah tua memberikan perisai dan nama Captain America kepada Sam Wilson.",
                "https://id.wikipedia.org/wiki/Avengers:_Endgame"
        );

        tambahFilm(film4, db);
        idFilm++;

        //film5
        try{
            tempDate = sdFormat.parse("25/11/2020 00:00");
        } catch (ParseException er) {
            er.printStackTrace();
        }

        Film film5 = new Film(
                idFilm,
                "No Time to Die",
                tempDate,
                storeImageFile(R.drawable.film5),
                "cover film No Time to Die",
                "Produser : Michael G. Wilson",
                "Plot seri ke-25 franchise tokoh agen rahasia 007 itu bercerita mengenai kehidupan Bond usai pensiun dari dunia spionase, namun terpaksa kembali beraksi lantaran ingin membantu seorang teman.\n" +
                        "\n" +
                        "Kehidupan yang damai usai terlepas dari dunia agen rahasia, dirasakan James Bond hanya dalam kurun waktu yang terbilang singkat. Masa pensiunnya terganggu saat teman lamanya di Central Intelligence Agency (CIA), Felix Leiter, muncul dan meminta bantuan.\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "Keinginan Bond untuk memberikan bantuan, membawanya berpetualang menelusuri jejak penjahat misterius yang dipersenjatai dengan teknologi baru yang berbahaya. Aksi kejar-kejaran, perkelahian, dan saling tembak mewarnai perjalanan James membongkar misteri.\n" +
                        "\n" +
                        "Film tentu saja menghadirkan sederet aksi yang melibatkan mobil-mobil mewah, perempuan cantik, adegan operasi khusus, serta teknologi khas agen rahasia yang berkecimpung di dunia spionase.\n" +
                        "\n" +
                        "Film No Time To Die, menjadi film kelima bagi aktor Daniel Craig memerankan tokoh agen rahasia 007, sekaligus menjadi film terakhir baginya memerankan tokoh James Bond.\n" +
                        "\n" +
                        "Pasalnya, usia Daniel dinilai sudah tak lagi cocok memerankan tokoh Bond yang selalu digambarkan berusia 33 tahun.\n" +
                        "\n" +
                        "Hingga saat ini, Craig telah bermain dalam 5 judul film James Bond, yakni Casino Royale, Quantum of Solace, Skyfall, Spectre dan No Time To Die.\n" +
                        "\n" +
                        "Selain Daniel Craig, film No Time To Die juga dibintangi sederet aktor dan aktris ternama, di antaranya, Christoph Waltz, Ralph Fiennes, Naomie Harris, Rory Kinnear, serta Léa Seydoux.\n" +
                        "\n" +
                        "Juga menampilkan Lashana Lynch, Ana de Armas, Ben Whishaw, dan aktor pemeran Freddy Mercury dalam film Queen, Rami Malek.",
                "https://en.wikipedia.org/wiki/No_Time_to_Die"
        );

        tambahFilm(film5, db);
    }
}
