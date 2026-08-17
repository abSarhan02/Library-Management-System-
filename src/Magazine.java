public class Magazine extends LibraryItem implements Renewable {

    private final int issueNumber;

    private static final int RENEWAL_LIMIT = 1;

    public Magazine(
            String catalogueId,
            String title,
            int issueNumber) {

        super(catalogueId, title);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public double calculateFine(int daysOverdue) {

        if (daysOverdue <= 0) {
            return 0;
        }

        double fine = daysOverdue * 3.0;

        return Math.min(fine, 30.0);
    }

    @Override
    public int getLoanPeriod() {
        return 7;
    }

    @Override
    public String getCategory() {
        return "Magazine";
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