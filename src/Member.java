public class Member {

    private String name;
    private final String membershipId;

    private double balance;
    private int itemsHeld;

    public Member(String name, String membershipId) {
        this(name, membershipId, 0.0);
    }

    public Member(String name, String membershipId, double balance) {
        this.name = name;
        this.membershipId = membershipId;
        this.balance = Math.max(0, balance);
        this.itemsHeld = 0;
    }

    public String getName() {
        return name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public double getBalance() {
        return balance;
    }

    public int getItemsHeld() {
        return itemsHeld;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canBorrow() {
        return itemsHeld < 3 && balance <= 100.0;
    }

    public void recordBorrowing() {
        itemsHeld++;
    }

    public void recordReturn() {
        if (itemsHeld > 0) {
            itemsHeld--;
        }
    }

    public void chargeFine(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean payFine(double amount) {

        if (amount <= 0) {
            return false;
        }

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "Name: " + name
                + " - ID: " + membershipId
                + " - Items held: " + itemsHeld
                + " - Balance: " + String.format("%.2f", balance) + " EGP";
    }
}