package com.bk.donglt.patient_manager.entity;

import com.bk.donglt.patient_manager.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "file")
@NoArgsConstructor
public class FileUpload extends BaseEntity {
    private String owner;
    private String file;

    public FileUpload(String owner, String file) {
        this.owner = owner;
        this.file = file;
    }
}
