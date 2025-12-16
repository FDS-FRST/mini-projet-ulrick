public enum NivoDifikilte {
    FASIL(0.8),
    NORMAL(1.0),
    DIFFICIL(1.2);

    private final double multiplicateurStats;

    NivoDifikilte(double multiplicateurStats) {
        this.multiplicateurStats = multiplicateurStats;
    }

    public double getMultiplicateurStats() {
        return multiplicateurStats;
    }
}