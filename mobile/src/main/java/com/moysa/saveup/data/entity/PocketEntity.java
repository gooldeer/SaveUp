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
    private int amount;
    private String name;

    protected PocketEntity(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        amount = in.readInt();
        name = in.readString();
    }

    public PocketEntity() {
    }

    public PocketEntity(Pocket pocket) {
        id = pocket.getId();
        userId = pocket.getUserId();
        amount = pocket.getAmount();
        name = pocket.getName();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(amount);
        dest.writeString(name);
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
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
        return amount == that.amount && (name != null ? name.equals(that.name) : that.name == null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + amount;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
