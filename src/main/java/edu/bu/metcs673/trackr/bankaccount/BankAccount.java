package edu.bu.metcs673.trackr.bankaccount;

import static edu.bu.metcs673.trackr.bankaccount.BankAccount.ACCOUNT_STATUS.ACTIVE;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import edu.bu.metcs673.trackr.common.EncryptionDoubleConverter;
import edu.bu.metcs673.trackr.common.EncryptionStringConverter;
import org.apache.commons.lang3.StringUtils;

import edu.bu.metcs673.trackr.common.CommonConstants;
import edu.bu.metcs673.trackr.transaction.Transaction;
import edu.bu.metcs673.trackr.user.TrackrUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BANK_ACCOUNTS")
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
	private TrackrUser user;

	@Column(nullable = false, length = 20)
	@NotNull(message = CommonConstants.BLANK_ACCOUNT_TYPE)
	@Enumerated(EnumType.STRING)
	@Convert(converter = EncryptionStringConverter.class)
	private ACCOUNT_TYPE accountType;

	@Column(nullable = false)
	@Size(min = 1, max = 255, message = CommonConstants.INVALID_ACCOUNT_DESC_LENGTH)
	@Convert(converter = EncryptionStringConverter.class)
	private String accountDescription;

	@Column(nullable = false, precision = 2)
	@PositiveOrZero(message = CommonConstants.INVALID_BALANCE_VALUE)
	@Convert(converter = EncryptionDoubleConverter.class)
	private double balance;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = EncryptionStringConverter.class)
	private ACCOUNT_STATUS status;
	
	@OneToMany
	@JoinColumn(name = "transactions")
	private List<Transaction> transactions;

	// enum used for the 'status' field in the BankAccount object
	public static enum ACCOUNT_STATUS {
		ACTIVE, INACTIVE;

		public boolean contains(String status) {
			for (ACCOUNT_STATUS acctStatus : ACCOUNT_STATUS.values()) {
				if (StringUtils.equals(acctStatus.toString(), status)) {
					return true;
				}
			}
			return false;
		}
	};

	// enum used for the 'accountType' field in the BankAccount object
	public static enum ACCOUNT_TYPE {
		CHECKING, SAVING, RETIREMENT, OTHER;

		public boolean contains(String status) {
			for (ACCOUNT_TYPE acctStatus : ACCOUNT_TYPE.values()) {
				if (StringUtils.equals(acctStatus.toString(), status)) {
					return true;
				}
			}
			return false;
		}
	}
	
	public BankAccount(BankAccountDTO dto, TrackrUser user) {
		this.id = 0L;
		this.user = user;
		this.accountDescription = dto.getAccountDescription();
		this.accountType = dto.getAccountType();
		this.balance = dto.getBalance();
		this.status = ACTIVE;
	}
}
