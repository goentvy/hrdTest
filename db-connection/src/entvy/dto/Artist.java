package entvy.dto;

public class Artist {
    private int artistNo;
    private String artistName;
    private String debutDate;
    private String genre;
    private String agency;

    public Artist(int artistNo, String artistName, String debutDate, String genre, String agency) {
        this.artistNo = artistNo;
        this.artistName = artistName;
        this.debutDate = debutDate;
        this.genre = genre;
        this.agency = agency;
    }

    public int getArtistNo() { return artistNo; }
    public String getArtistName() { return artistName; }
    public String getDebutDate() { return debutDate; }
    public String getGenre() { return genre; }
    public String getAgency() { return agency; }
}