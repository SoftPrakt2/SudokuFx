package logic;

public enum Boxcolor {
    C0("efefef"), 
    C1("97c1a9"), 
    C2("cab08b"),
    C3("dfd8ab"), 
    C4("80adbc"), 
    C5("d5a1a3"), 
    C6("adb5be"), 
    C7("eaeee0"), 
    C8("957DAD"),
    C9("FFDFD3");

    private String hexadec;

    Boxcolor(String hd) {
        this.hexadec = hd;
    }

    public String getColor() {
        return hexadec;
    }
}