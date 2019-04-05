package pqd.route;

public enum Headers {
    SEQNUM("seqnum");

    private final String seqnum;

    Headers(String seqnum) {
        this.seqnum = seqnum;
    }

    public String header() {
        return seqnum;
    }
}
