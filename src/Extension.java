public enum Extension {
    PBM,
    PPM,
    PGM;


    @Override
    public String toString() {
        switch (this) {
            case PBM:
                return ".pbm";
            case PPM:
                return ".ppm";
            case PGM:
                return ".pgm";
        }

        return "";
    }
}
