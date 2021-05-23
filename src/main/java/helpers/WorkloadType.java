package helpers;

public enum WorkloadType {
    Office("Office"),
    Gaming("Gaming"),
    PhotoEditing("Photo Editing"),
    VideoEditing("Video Editing"),
    Rendering3D("3D Rendering");

    private final String name;

    WorkloadType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static WorkloadType toEnum(String val) {
        for(var e : WorkloadType.values()) {
            if(e.toString().equals(val))
                return e;
        }
        return null;
    }
}
