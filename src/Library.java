public class Library {

    private LibraryItem[] catalogue;
    private Member[] members;

    private int itemCount;
    private int memberCount;

    public Library(int catalogueCapacity, int memberCapacity) {

        catalogue = new LibraryItem[catalogueCapacity];
        members = new Member[memberCapacity];

        itemCount = 0;
        memberCount = 0;
    }

    // -------------------------
    // REGISTRATION
    // -------------------------

    public boolean registerItem(LibraryItem item) {

        if (itemCount >= catalogue.length) {
            System.out.println("Catalogue is full.");
            return false;
        }

        if (findItem(item.getCatalogueId()) != null) {
            System.out.println("An item with this ID already exists.");
            return false;
        }

        catalogue[itemCount] = item;
        itemCount++;

        return true;
    }

    public boolean registerMember(Member member) {

        if (memberCount >= members.length) {
            System.out.println("Member register is full.");
            return false;
        }

        if (findMember(member.getMembershipId()) != null) {
            System.out.println("A member with this ID already exists.");
            return false;
        }

        members[memberCount] = member;
        memberCount++;

        return true;
    }

    // -------------------------
    // SEARCH
    // -------------------------

    public LibraryItem findItem(String id) {

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getCatalogueId().equalsIgnoreCase(id)) {
                return catalogue[i];
            }
        }

        return null;
    }

    public Member findMember(String id) {

        for (int i = 0; i < memberCount; i++) {

            if (members[i].getMembershipId().equalsIgnoreCase(id)) {
                return members[i];
            }
        }

        return null;
    }

    // -------------------------
    // LISTINGS
    // -------------------------

    public void displayCatalogue() {

        if (itemCount == 0) {
            System.out.println("Catalogue is empty.");
            return;
        }

        for (int i = 0; i < itemCount; i++) {
            catalogue[i].display();
        }
    }

    public void displayItemsByStatus(ItemStatus status) {

        boolean found = false;

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus() == status) {
                catalogue[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items with status " + status);
        }
    }

    public void displayMembers() {

        if (memberCount == 0) {
            System.out.println("No members registered.");
            return;
        }

        for (int i = 0; i < memberCount; i++) {
            System.out.println(members[i]);
        }
    }

    // -------------------------
    // BORROW
    // -------------------------

    public boolean lendItem(String itemId, String memberId) {

        LibraryItem item = findItem(itemId);
        Member member = findMember(memberId);

        if (item == null) {
            System.out.println("Item not found.");
            return false;
        }

        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }

        if (item.getStatus() != ItemStatus.AVAILABLE) {
            System.out.println("Item is not available.");
            return false;
        }

        if (!member.canBorrow()) {
            System.out.println("Member is not allowed to borrow.");
            return false;
        }

        if (item.lendTo(member)) {

            member.recordBorrowing();

            System.out.println(
                    "Item borrowed successfully for "
                            + item.getLoanPeriod()
                            + " days."
            );

            return true;
        }

        return false;
    }

    // -------------------------
    // RETURN
    // -------------------------

    public boolean returnItem(String itemId, String memberId) {

        LibraryItem item = findItem(itemId);

        if (item == null) {
            System.out.println("Item not found.");
            return false;
        }

        if (item.getStatus() != ItemStatus.ON_LOAN) {
            System.out.println("This item is not currently on loan.");
            return false;
        }

        Member member = findMember(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }

        member.recordReturn();
        item.takeBack();

        System.out.println("Item returned successfully.");

        return true;
    }

    // -------------------------
    // RENEW
    // -------------------------

    public void renewItem(String itemId) {

        LibraryItem item = findItem(itemId);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (!(item instanceof Renewable)) {

            System.out.println(
                    item.getCategory()
                            + " items cannot be renewed."
            );

            return;
        }

        Renewable renewableItem = (Renewable) item;

        if (renewableItem.renew()) {

            int remaining =
                    renewableItem.getRenewalLimit()
                            - item.getRenewalCount();

            System.out.println("Loan renewed successfully.");
            System.out.println(
                    "Renewals remaining: " + remaining
            );

        } else {

            System.out.println(
                    "The loan could not be renewed."
            );
        }
    }

    // -------------------------
    // STATISTICS
    // -------------------------

    public int getItemsOnLoan() {

        int count = 0;

        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus()
                    == ItemStatus.ON_LOAN) {

                count++;
            }
        }

        return count;
    }

    public double getLoanRate() {

        if (itemCount == 0) {
            return 0;
        }

        return ((double) getItemsOnLoan() / itemCount) * 100;
    }

    public double getTotalOutstanding() {

        double total = 0;

        for (int i = 0; i < memberCount; i++) {
            total += members[i].getBalance();
        }

        return total;
    }

    public void displayReport() {

        System.out.println();
        System.out.println("===== LIBRARY REPORT =====");

        System.out.println(
                "Catalogue size: " + itemCount
        );

        System.out.println(
                "Items ever catalogued: "
                        + LibraryItem.getTotalItemsCatalogued()
        );

        System.out.println(
                "Items on loan: "
                        + getItemsOnLoan()
        );

        System.out.printf(
                "Loan rate: %.2f%%%n",
                getLoanRate()
        );

        System.out.printf(
                "Total outstanding balance: %.2f EGP%n",
                getTotalOutstanding()
        );
    }
}