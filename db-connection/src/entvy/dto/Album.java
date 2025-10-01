package entvy.dto;

public class Album {
    private int albumNo;
    private int artistNo;
    private String albumTitle;
    private String releaseDate;
    private int sales;

    public Album(int albumNo, int artistNo, String albumTitle, String releaseDate, int sales) {
        this.albumNo = albumNo;
        this.artistNo = artistNo;
        this.albumTitle = albumTitle;
        this.releaseDate = releaseDate;
        this.sales = sales;
    }

    public int getAlbumNo() { return albumNo; }
    public int getArtistNo() { return artistNo; }
    public String getAlbumTitle() { return albumTitle; }
    public String getReleaseDate() { return releaseDate; }
    public int getSales() { return sales; }
}