代码如下：

```
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledTask {

    public static void main(String[] args) {
        // 输入任务开始时间，格式为 "yyyy-MM-dd HH:mm:ss"
        String startTime = "2021-01-01 00:00:00";
        // 输入周期单位，包括：分、时、天、周
        TimeUnit timeUnit = TimeUnit.DAYS;
        // 输入周期
        int period = 1;

        // 创建ScheduledExecutorService对象
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // 计算任务开始时间距离当前时间的时间差
        long initialDelay = getInitialDelay(startTime);

        // 根据周期单位和周期设置定时任务
        if (timeUnit == TimeUnit.MINUTES) {
            executor.scheduleWithFixedDelay(new Task(), initialDelay, period, TimeUnit.MINUTES);
        } else if (timeUnit == TimeUnit.HOURS) {
            executor.scheduleWithFixedDelay(new Task(), initialDelay, period, TimeUnit.HOURS);
        } else if (timeUnit == TimeUnit.DAYS) {
            executor.scheduleWithFixedDelay(new Task(), initialDelay, period, TimeUnit.DAYS);
        } else if (timeUnit == TimeUnit.WEEKS) {
            executor.scheduleWithFixedDelay(new Task(), initialDelay, period * 7, TimeUnit.DAYS);
        }
    }

    // 计算任务开始时间距离当前时间的时间差
    private static long getInitialDelay(String startTime) {
        long initialDelay = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = sdf.parse(startTime);
            Date now = new Date();
            initialDelay = start.getTime() - now.getTime();
            if (initialDelay < 0) {
                initialDelay = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return initialDelay;
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            // 任务执行的具体逻辑
            System.out.println("Task executed at " + new Date());
        }
    }
}
```

在上面的代码中，我们使用ScheduledExecutorService来创建定时任务。首先，我们输入任务开始时间、执行周期单位和周期。然后，我们创建ScheduledExecutorService对象，并根据周期单位和周期设置定时任务。

在设置定时任务时，我们使用了scheduleAtFixedRate方法。该方法接受三个参数：任务对象、任务开始时间距离当前时间的时间差、任务执行周期。根据周期单位和周期，我们选择不同的时间单位来设置任务执行周期。

最后，我们定义了一个Task类，实现Runnable接口，并在其中编写了任务执行的具体逻辑。在这里，我们只是简单地输出了任务执行的时间。实际上，我们可以在这里编写任何我们想要执行的逻辑。