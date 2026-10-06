public abstract class LibraryItem {

    private final String catalogueId;
    private final String title;

    // Current status of the item
    private ItemStatus status;
    private String borrowerName;

    // Number of times the current loan was renewed
    protected int renewalCount;

    private static final String LIBRARY_NAME = "Bayt Al Hekma";

    // Count all items created in the system
    private static int totalItemsCatalogued = 0;


    public LibraryItem(String catalogueId, String title) {
        this.catalogueId = catalogueId;
        this.title = title;

        // New items are available by default
        this.status = ItemStatus.AVAILABLE;
        this.borrowerName = null;
        this.renewalCount = 0;

        totalItemsCatalogued++;
    }


    public String getCatalogueId() {
        return catalogueId;
    }

    public String getTitle() {
        return title;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getRenewalCount() {
        return renewalCount;
    }

    public static String getLibraryName() {
        return LIBRARY_NAME;
    }

    public static int getTotalItemsCatalogued() {
        return totalItemsCatalogued;
    }


    public boolean lendTo(Member member) {

        // Only available items can be borrowed
        if (status != ItemStatus.AVAILABLE) {
            return false;
        }

        status = ItemStatus.ON_LOAN;
        borrowerName = member.getName();

        return true;
    }


    public final void takeBack() {

        // Reset the item after a return
        status = ItemStatus.AVAILABLE;
        borrowerName = null;
        renewalCount = 0;
    }


    public void markReserved() {

        if (status == ItemStatus.AVAILABLE) {
            status = ItemStatus.RESERVED;
        }
    }


    public void markLost() {
        status = ItemStatus.LOST;
    }


    public void makeAvailable() {

        status = ItemStatus.AVAILABLE;
        borrowerName = null;
        renewalCount = 0;
    }


    protected void recordRenewal() {
        renewalCount++;
    }


    // Each type of item has its own rules
    public abstract double calculateFine(int daysOverdue);

    public abstract int getLoanPeriod();

    public abstract String getCategory();


    public void display() {

        String borrower;

        if (borrowerName == null) {
            borrower = "-";
        } else {
            borrower = borrowerName;
        }

        System.out.println(
                catalogueId
                        + " - "
                        + getCategory()
                        + " - "
                        + title
                        + " - "
                        + status
                        + " - Borrower: "
                        + borrower
                        + " - Loan period: "
                        + getLoanPeriod()
                        + " days"
                        + " - 1-day fine: "
                        + String.format("%.2f", calculateFine(1))
                        + " EGP"
        );
    }
}
