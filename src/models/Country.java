package models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Country extends BaseModel {
    public Country(int countryId, String country) {
super(countryId, country);
    }


    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    @Override
    public String toString() {
        return getName();
    }

    public int getCountryId() {
        return getId();
    }

    public void setCountryId(int countryId) {
        setId(countryId);
    }

    public String getCountry() {
        return getName();
    }

    public void setCountry(String country) {
        setName(country);
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
