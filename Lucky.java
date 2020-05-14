/*

Цитата (https://www.rsdn.org/article/baseserv/mt.xml):

Что произойдет, если один поток еще не закончил работать с каким-либо общим ресурсом,
а система переключилась на другой поток, использующий тот же ресурс?
Произойдет штука очень неприятная. А результат работы этих потоков может чрезвычайно сильно отличаться от задуманного.
Такие конфликты могут возникнуть и между потоками, принадлежащими различным процессам.
Всегда, когда два или более потоков используют какой-либо общий ресурс, возникает эта проблема.

Нужно сделать так, чтобы ресурс использовался одним потоком одновременно.
Используем синхронизацию потоков (thread synchronization).

Также создадим копию эл-та, чтобы обеспечить целостность, вместо синхронизации цикла

*/


public class Lucky {
    private static int x = 0;
    private static int count = 0;

    static class LuckyThread extends Thread {

        @Override
        public void run() {

            while (true) {
                int copy_x;
                synchronized (Lucky.class) {
                    if (x < 999999) copy_x = x++;
                    else break;
                }
                if ((copy_x % 10) + (copy_x / 10) % 10 + (copy_x / 100) % 10 == (copy_x / 1000)
                        % 10 + (copy_x / 10000) % 10 + (copy_x / 100000) % 10) {
                    System.out.println(copy_x);
                    synchronized (Lucky.class) {
                        count++;
                    }
                }
            }

        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }


}