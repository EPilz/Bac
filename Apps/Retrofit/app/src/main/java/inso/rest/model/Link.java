package inso.rest.model;

/**
 * Created by Elisabeth on 17.05.2015.
 */
public class Link {

    private String rel;
    private String href;

    public Link() {
    }

    public Link(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getLastValueId() {
        String[] elements = href.split("/+");
        int lastId = -1;
        for (String s : elements) {
            if(s.matches("[0-9]+")) {
                lastId = Integer.parseInt(s);
            }
        }

        return lastId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return rel.equals(link.rel);

    }

    @Override
    public int hashCode() {
        return rel.hashCode();
    }
}
