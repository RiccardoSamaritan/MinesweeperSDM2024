import javax.swing.ImageIcon;
import java.util.Objects;

public class IconWrapper {
    private final ImageIcon icon;

    public IconWrapper(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IconWrapper that = (IconWrapper) o;
        return Objects.equals(icon.getDescription(), that.icon.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon.getDescription());
    }
}