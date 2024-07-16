package hellojpa;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public class BaseEntity {

    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModifiedDate;
}
