package sinhblackgaming.mciw.modes;

import net.minecraft.nbt.CompoundNBT;
import sinhblackgaming.mciw.MCIWMod;

public class Mode {
    public static String STATE_RUNNING = "running";
    public static String STATE_STOPPED = "stopped";
    public static String STATE_PAUSED = "paused";

    public String state;
    public String name;

    public Mode(String name){
        this.name = name;
        this.state = STATE_STOPPED;
    }

    public void start(){
        if (this.state.equals(STATE_STOPPED)){
            this.onStart();
            this.state = STATE_RUNNING;
        }
    }

    public void stop(){
        if (this.state.equals(STATE_RUNNING) ||
            this.state.equals(STATE_PAUSED)) {
            this.state = STATE_STOPPED;
            this.onStop();
        }
    }

    public void pause(){
        if (this.state.equals(STATE_RUNNING)) {
            this.onPause();
            this.changeState(STATE_PAUSED);
        }
    }

    public void unPause(){
        if (this.state.equals(STATE_PAUSED)){
            this.onUnPause();
            this.changeState(STATE_RUNNING);
        }
    }

    public void changeState(String state){
        this.state = state;
    }

    public boolean isRunning(){
        return this.state.equals(STATE_RUNNING);
    }

    public void onStart(){}
    public void onStop(){}
    public void onPause(){}
    public void onUnPause(){}
    public void onUpdate(){}

    public String getState(){return this.state;}
    public void setState(String state){this.state = state;}

    public void writeNBT(CompoundNBT tag){
         tag.putString(MCIWMod.MODID + ".modestate." + name, state);
    }

    public void readNBT(CompoundNBT nbt){
        String state = nbt.getString(MCIWMod.MODID + ".modestate." + name);
        if (state.isEmpty()) {
            state = STATE_STOPPED;
        }
        setState(state);
    }
}
