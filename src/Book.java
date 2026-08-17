public class Book extends LibraryItem implements Renewable {

    private final String author;
    private final int pageCount;

    private static final int RENEWAL_LIMIT = 2;

    public Book(
            String catalogueId,
            String title,
            String author,
            int pageCount) {

        super(catalogueId, title);

        this.author = author;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    @Override
    public double calculateFine(int daysOverdue) {

        if (daysOverdue <= 0) {
            return 0;
        }

        return daysOverdue * 5.0;
    }

    @Override
    public int getLoanPeriod() {
        return 14;
    }

    @Override
    public String getCategory() {
        return "Book";
    }

    @Override
    public boolean renew() {

        if (getStatus() != ItemStatus.ON_LOAN) {
            return false;
        }

        if (renewalCount >= RENEWAL_LIMIT) {
            return false;
        }

        recordRenewal();
        return true;
    }

    @Override
    public int getRenewalLimit() {
        return RENEWAL_LIMIT;
    }
}