package Listeners;

import Events.*;
import Logic.VBD;
import Logic.VRD;
import Logic.BSCLayer;

public interface VisualManagerListener{
    VBD perform(AddVBDEvent e);
    VRD perform(AddVRDEvent e);
    void perform(AddBSCLayerEvent e);
    void perform(RemoveBSCLayerEvent e);
    void perform(SaveInfoEvent e);

}
