package xyz.kwin.dailybonus.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 签到状态
 *
 * @author Kwin
 * @since 2020/8/16 21:17
 */
@Getter
@Setter
@Accessors(chain = true)
public class CheckinStatus {
    private Boolean status;
    private String message;
    private CheckinRecord checkinRecord;

    public static final String MESSAGE_ALREADY_CHECKIN = "Already checkin, no need to checkin twice!";
    public static final String MESSAGE_CHECKIN_SUCCESS = "Checkin success!";

    public static CheckinStatus alreadyCheckin(CheckinRecord checkinRecord) {
        return new CheckinStatus().setStatus(true).setMessage(MESSAGE_ALREADY_CHECKIN).setCheckinRecord(checkinRecord);
    }

    public static CheckinStatus checkinSuccess(CheckinRecord checkinRecord) {
        return new CheckinStatus().setStatus(true).setMessage(MESSAGE_CHECKIN_SUCCESS).setCheckinRecord(checkinRecord);
    }
}
