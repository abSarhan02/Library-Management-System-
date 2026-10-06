public class DVD extends LibraryItem {

    private final int runtime;


    public DVD(
            String catalogueId,
            String title,
            int runtime) {

        super(catalogueId, title);
        this.runtime = runtime;
    }


    public int getRuntime() {
        return runtime;
    }


    @Override
    public double calculateFine(int daysOverdue) {

        if (daysOverdue <= 0) {
            return 0;
        }

        // DVDs have a higher daily fine
        return daysOverdue * 15.0;
    }


    @Override
    public int getLoanPeriod() {
        return 3;
    }


    @Override
    public String getCategory() {
        return "DVD";
    }
}
