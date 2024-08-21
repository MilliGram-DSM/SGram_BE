package bhyun_pt.sgram_server.global.socket.exception;

import bhyun_pt.sgram_server.global.error.CustomException;
import bhyun_pt.sgram_server.global.error.ErrorCode;
import bhyun_pt.sgram_server.global.error.ErrorReponse;
import bhyun_pt.sgram_server.global.socket.property.SocketProperty;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class SocketExceptionListener implements ExceptionListener {
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        runExceptionHandling(e, client);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        runExceptionHandling(e, client);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        runExceptionHandling(e, client);
        client.disconnect();
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        runExceptionHandling(e, client);
    }

    @Override
    public void onPongException(Exception e, SocketIOClient socketIOClient) {

    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        return false;
    }

    @Override
    public void onAuthException(Throwable throwable, SocketIOClient socketIOClient) {

    }

    private void runExceptionHandling(Exception e, SocketIOClient client) {
        ErrorCode errorCode;
        if (e.getCause() instanceof CustomException) {
            errorCode = ((CustomException) e.getCause()).getErrorCode();
        } else {
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }
        ErrorReponse message = ErrorReponse
                .builder()
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .build();

        client.sendEvent(SocketProperty.ERROR, message);
    }

}
