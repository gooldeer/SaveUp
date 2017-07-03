package com.moysa.saveup.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.moysa.saveup.data.model.Transaction;

import java.util.Date;

/**
 * Created by Sergey Moysa
 */

@Entity(tableName = "transactions",
        foreignKeys = {
                @ForeignKey(entity = PocketEntity.class,
                        parentColumns = "id",
                        childColumns = "pocketId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "pocketId")
        }
)
public class TransactionEntity implements Transaction, Parcelable {

    public static final Creator<TransactionEntity> CREATOR = new Creator<TransactionEntity>() {
        @Override
        public TransactionEntity createFromParcel(Parcel in) {
            return new TransactionEntity(in);
        }

        @Override
        public TransactionEntity[] newArray(int size) {
            return new TransactionEntity[size];
        }
    };
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int pocketId;
    private float amount;
    private String comment;
    private Date date;

    public TransactionEntity(Parcel in) {
        id = in.readInt();
        pocketId = in.readInt();
        amount = in.readFloat();
        comment = in.readString();
        date = new Date(in.readLong());
    }

    public TransactionEntity() {
    }

    public TransactionEntity(Transaction transaction) {
        id = transaction.getId();
        pocketId = transaction.getPocketId();
        amount = transaction.getAmount();
        comment = transaction.getComment();
        date = transaction.getDate();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPocketId() {
        return pocketId;
    }

    public void setPocketId(int pocketId) {
        this.pocketId = pocketId;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(pocketId);
        dest.writeFloat(amount);
        dest.writeString(comment);
        dest.writeLong(date.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntity)) return false;

        TransactionEntity that = (TransactionEntity) o;

        if (id != that.id) return false;
        if (pocketId != that.pocketId) return false;
        if (amount != that.amount) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null && (date != null ? date.equals(that.date) : that.date == null);

    }

    @Override
    public int hashCode() {
        float result = id;
        result = 31 * result + pocketId;
        result = 31 * result + amount;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return (int) result;
    }
}
