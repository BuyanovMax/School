package ru.hogwarts.school.model;
import liquibase.pro.packaged.L;
import lombok.*;
import ru.hogwarts.school.auditEntity.AuditableEntity;
//import org.hibernate.envers.Audited;
//import org.hibernate.envers.RelationTargetAuditMode;


import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avatar extends AuditableEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "long_size")
    private Long longSize;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "data")
    private byte[] data;

    @OneToOne
    private Student student;

    public long getLongSize() {
        return longSize;
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


