package org.example.quanlysu5.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
public enum ErrorCode {
    INVALID_KEY(1001,"Invalid key", HttpStatus.BAD_REQUEST),
    FEATURE_NOT_FOUND(1002,"Feature Not Found", HttpStatus.NOT_FOUND),
    FEATURE_IS_EXIST(1003,"Feature Exist", HttpStatus.CONFLICT),
    ACCOUNT_NOT_FOUND(1002,"Không tìm thấy thông tin tài khoản", HttpStatus.NOT_FOUND),
    ACCOUNT_IS_EXIST(1003,"Trùng thông tin đăng nhập", HttpStatus.CONFLICT),
    CHUCVU_NOT_FOUND(1002,"Không tìm thấy thấy thông tin chức vụ", HttpStatus.NOT_FOUND),
    CHUCVU_IS_EXIST(1003,"thông tin chức vụ bị trùng", HttpStatus.CONFLICT),
    NHIEMVUNGAY_NOT_FOUND(1002,"Không tìm thấy thấy thông tin nhiệm vụ ngày", HttpStatus.NOT_FOUND),
    LYDOVANG_NOT_FOUND(1002,"Không tìm thấy thấy thông tin lý do vắng", HttpStatus.NOT_FOUND),
    LYDOVANG_IS_EXIST(1003,"thông tin lý do vắng bị trùng", HttpStatus.CONFLICT),
    CAPBAC_NOT_FOUND(1002,"Không tìm thấy thấy thông tin cấp bậc", HttpStatus.NOT_FOUND),
    CAPBAC_IS_EXIST(1003,"thông tin cấp bậc bị trùng", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(1002,"Role Not Found", HttpStatus.NOT_FOUND),
    ROLE_IS_EXIST(1003,"Role Exist", HttpStatus.CONFLICT),
    ROLE_IN_USE(1003, "Không thể xóa vai trò vì vai trò này đang được gán cho một hoặc nhiều tài khoản.", HttpStatus.CONFLICT),
    CTDANGCT_NOT_FOUND(1002,"Thông tin đơn công tác đảng, chính trị", HttpStatus.NOT_FOUND),
    CATRUC_TACCHIEN_EXIST(1002,"Ca trực ban Tác chiến đã tồn tại", HttpStatus.NOT_FOUND),
    CATRUC_CHIHUY_EXIST(1003,"Ca trực chỉ huy đã tồn tại", HttpStatus.CONFLICT),
    KHUNGGIOBAOCAO_NOT_FOUND(1002,"Không tìm thấy khung giờ hệ thống", HttpStatus.NOT_FOUND),
    KHUNGGIOBAOCAO_IS_EXIST(1003,"Trùng Khung Giờ Trực khác", HttpStatus.CONFLICT),
    DONVI_NOT_FOUND(1002,"Không tìm thấy đơn vị", HttpStatus.NOT_FOUND),
    DONVI_IS_EXIST(1003,"Đơn vị đã tồn tại", HttpStatus.CONFLICT),
    CATRUC_NOT_FOUND(1002,"Không tìm thấy ca trưc", HttpStatus.NOT_FOUND),
    CATRUC_IS_EXIST(1003,"Ca trực đã tồn tại", HttpStatus.CONFLICT),
    SHIFT_CANNOT_UPDATE_BECAUSE_REPORT_EXISTS(
            1006,
            "Không thể chỉnh sửa ca trực vì ca trực này đã được lập đơn báo cáo.",
            HttpStatus.CONFLICT
    ),
    DONBAOCAO_NOT_FOUND(1002,"Không tìm thấy đơn báo cáo", HttpStatus.NOT_FOUND),
    BAOCAO_NGOAI_KHUNG_GIO(1006,"Ngoài khung giờ báo ban cho phép của cấp đơn vị này", HttpStatus.BAD_REQUEST),
    DONBAOCAO_IS_EXIST(1003,"Đơn báo cáo đã tồn tại", HttpStatus.CONFLICT),
    TRUCBANTACCHIEN_NOT_FOUND(1002,"Không tìm thấy thông tin người trực ban tác chiên", HttpStatus.NOT_FOUND),
    TRUCBANTACCHIEN_IS_EXIST(1003,"Trùng thông tin người trực khác", HttpStatus.CONFLICT),
    TRUCCHIHUY_NOT_FOUND(1002,"Không tìm thấy thông tin người trực ban chỉ huy", HttpStatus.NOT_FOUND),
    TRUCCHIHUY_IS_EXIST(1003,"Trùng thông tin người trực khác", HttpStatus.CONFLICT),
    NHATKY_NOT_FOUND(1002,"Không tìm thấy thông tin nhật ký hệ thống", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(1003,"Mật khẩu không hợp lệ", HttpStatus.CONFLICT),
    ACCOUNT_LOCKED(1005,"Tài khoản đã bị khóa",HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1004,"Tên đăng nhập hoặc mật khẩu không đúng",HttpStatus.UNAUTHORIZED),
    UNCATEGORIZED(9999,"Uncategorized", HttpStatus.INTERNAL_SERVER_ERROR);
    ErrorCode(int Code,String Message, HttpStatusCode sponse){
        this.code = Code;
        this.message = Message;
        this.status = sponse;
    }
    private int code;
    private String message;
    private HttpStatusCode status;
}
