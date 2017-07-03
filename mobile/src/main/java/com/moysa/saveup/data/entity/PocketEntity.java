package com.moysa.saveup.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.moysa.saveup.data.model.Pocket;

/**
 * Created by Sergey Moysa
 */

@Entity(tableName = "pockets", foreignKeys = {
        @ForeignKey(entity = UserEntity.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )},
        indices = {
                @Index(value = "userId")
        }
)
public class PocketEntity implements Pocket, Parcelable {

    public static final Creator<PocketEntity> CREATOR = new Creator<PocketEntity>() {
        @Override
        public PocketEntity createFromParcel(Parcel in) {
            return new PocketEntity(in);
        }

        @Override
        public PocketEntity[] newArray(int size) {
            return new PocketEntity[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private float amount;
    private String name;
    private float savePercent;

    protected PocketEntity(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        amount = in.readFloat();
        name = in.readString();
        savePercent = in.readFloat();
    }

    public PocketEntity() {
    }

    public PocketEntity(Pocket pocket) {
        id = pocket.getId();
        userId = pocket.getUserId();
        amount = pocket.getAmount();
        name = pocket.getName();
        savePercent = pocket.getSavePercent();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeFloat(amount);
        dest.writeString(name);
        dest.writeFloat(savePercent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public float getSavePercent() {
        return savePercent;
    }

    public void setSavePercent(float savePercent) {
        this.savePercent = savePercent;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PocketEntity)) return false;

        PocketEntity that = (PocketEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (savePercent != that.savePercent) return false;
        return amount == that.amount && (name != null ? name.equals(that.name) : that.name == null);

    }

    @Override
    public int hashCode() {
        float result = id;
        result = 31 * result + userId;
        result = 31 * result + amount;
        result = 31 * result + savePercent;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return (int) result;
    }
}
