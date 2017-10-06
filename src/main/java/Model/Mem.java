package Model;

public class Mem {

    private String photoUrl;
    private String textToPhoto;

    public Mem (String photoUrl, String textToPhoto) {
        this.photoUrl = photoUrl;
        this.textToPhoto = textToPhoto;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getTextToPhoto() {
        return textToPhoto;
    }

}
