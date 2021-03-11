import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author lsy
 * @version 1.0
 * @date 2021/3/9 14:32
 */
public class EchoClient extends Thread{
    private final String host;
    private final int port;
    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
    }
    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            System.err.println("Usage: " + EchoClient.class.getSimpleName() + " <host> <port>");
//            return;
//        }
//        List<CompletableFuture> allTaskList = new LinkedList<>();
        for (int i=0;i<10000;i++) {
            System.out.println(i);
//            CompletableFuture borrowerTaskFuture = CompletableFuture.runAsync(() -> {
//                try {
                    new EchoClient("127.0.0.1", 8899).start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//            allTaskList.add(borrowerTaskFuture);
        }
//        CompletableFuture allTaskFuture = CompletableFuture.allOf(allTaskList.toArray(new CompletableFuture[allTaskList.size()]));
        


    }
}
