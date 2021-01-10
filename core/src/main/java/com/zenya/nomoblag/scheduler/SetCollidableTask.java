package com.zenya.nomoblag.scheduler;

import com.zenya.nomoblag.NoMobLag;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SetCollidableTask implements NMLTask {
    private BukkitTask bukkitTask;
    private LivingEntity entity;

    public SetCollidableTask(LivingEntity entity) {
        this.entity = entity;
        runTask();
    }

    @Override
    public TaskKey getKey() {
        return TaskKey.SET_COLLIDABLE_TASK;
    }

    @Override
    public void runTask() {
        int cancelAfter = 20*10;

        bukkitTask = new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if(count >= cancelAfter) this.cancel();

                if(!entity.isCollidable()) {
                    entity.setCollidable(true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            entity.setCollidable(false);
                        }
                    }.runTaskLaterAsynchronously(NoMobLag.getInstance(), 10);
                }
            }
        }.runTaskTimerAsynchronously(NoMobLag.getInstance(), 0, 1);
    }

    @Override
    public BukkitTask getTask() {
        return bukkitTask;
    }
}