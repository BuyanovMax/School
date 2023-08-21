package ru.hogwarts.school.model;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private Long longSize;
    private String mediaType;
    private byte[] data;

    @OneToOne
    private Student student;

    public long getLongSize() {
        return this.longSize;
    }
    public String getFilePath() {
        return filePath;
    }
    public Student getStudent() {
        return student;
    }
    public byte[] getData() {
        return data;
    }
    public String getMediaType() {
        return mediaType;
    }


}


