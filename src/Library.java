public class Library {

    // Store all library items and registered members
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

        // Check if there is still space in the catalogue
        if (itemCount >= catalogue.length) {
            System.out.println("Catalogue is full.");
            return false;
        }

        // Every item needs a unique catalogue ID
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

        // Don't allow duplicate membership IDs
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

        // Search the catalogue by ID
        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i]
                    .getCatalogueId()
                    .equalsIgnoreCase(id)) {

                return catalogue[i];
            }
        }

        return null;
    }


    public Member findMember(String id) {

        // Search a member by membership ID
        for (int i = 0; i < memberCount; i++) {

            if (members[i]
                    .getMembershipId()
                    .equalsIgnoreCase(id)) {

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

        // Display every item using its own display method
        for (int i = 0; i < itemCount; i++) {
            catalogue[i].display();
        }
    }


    public void displayItemsByStatus(ItemStatus status) {

        boolean found = false;

        // Show only items with the selected status
        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus() == status) {
                catalogue[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No items with status " + status
            );
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

        // Find both the item and the member
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


        // The item must be available
        if (item.getStatus() != ItemStatus.AVAILABLE) {
            System.out.println("Item is not available.");
            return false;
        }


        // Check the member borrowing limits
        if (!member.canBorrow()) {
            System.out.println(
                    "Member is not allowed to borrow."
            );
            return false;
        }


        if (item.lendTo(member)) {

            // Update how many items the member currently has
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


        // Only borrowed items can be returned
        if (item.getStatus() != ItemStatus.ON_LOAN) {
            System.out.println(
                    "This item is not currently on loan."
            );
            return false;
        }


        Member member = findMember(memberId);


        if (member == null) {
            System.out.println("Member not found.");
            return false;
        }


        // Update both the member and the item
        member.recordReturn();
        item.takeBack();


        System.out.println(
                "Item returned successfully."
        );

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


        // Not every type of library item can be renewed
        if (!(item instanceof Renewable)) {

            System.out.println(
                    item.getCategory()
                            + " items cannot be renewed."
            );

            return;
        }


        // We know the item implements Renewable, so we can cast it
        Renewable renewableItem = (Renewable) item;


        if (renewableItem.renew()) {

            int remaining =
                    renewableItem.getRenewalLimit()
                            - item.getRenewalCount();


            System.out.println(
                    "Loan renewed successfully."
            );

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


        // Count all items currently borrowed
        for (int i = 0; i < itemCount; i++) {

            if (catalogue[i].getStatus()
                    == ItemStatus.ON_LOAN) {

                count++;
            }
        }

        return count;
    }


    public double getLoanRate() {

        // Avoid division by zero
        if (itemCount == 0) {
            return 0;
        }

        return ((double) getItemsOnLoan() / itemCount) * 100;
    }


    public double getTotalOutstanding() {

        double total = 0;

        // Add all unpaid balances
        for (int i = 0; i < memberCount; i++) {
            total += members[i].getBalance();
        }

        return total;
    }


    public void displayReport() {

        System.out.println();
        System.out.println(
                "===== LIBRARY REPORT ====="
        );


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
