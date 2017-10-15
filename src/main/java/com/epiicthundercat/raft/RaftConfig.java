package com.epiicthundercat.raft;

import net.minecraftforge.common.config.Config;

public class RaftConfig {

    

    @Config.Comment("Seagull Spawn Rate. [Default: 100]")
    public static int Seagull_Spawn_Rate = 100;
    @Config.Comment("Fish Spawn Rate. [Default: 100]")
    public static int Fish_Spawn_Rate = 100;
    @Config.Comment("Shark Spawn Rate. [Default: 70]")
    public static int Shark_Spawn_Rate = 70;
    @Config.Comment("Eel Spawn Rate. [Default: 70]")
    public static int Eel_Spawn_Rate = 70;
    
    @Config.Comment("Seagull Spawn Min Amount. [Default: 5]")
    public static int Seagull_Spawn_MIN_Amount = 5;
    @Config.Comment("Fish Spawn Min Amount. [Default: 4]")
    public static int Fish_Spawn_MIN_Amount = 4;
    @Config.Comment("Shark Spawn Min Amount. [Default: 1]")
    public static int Shark_Spawn_MIN_Amount = 1;
    @Config.Comment("Eel Spawn Min Amount. [Default: 4]")
    public static int Eel_Spawn_MIN_Amount = 4;
    @Config.Comment("Seagull Spawn Max Amount. [Default: 9]")
    public static int Seagull_Spawn_MAX_Amount = 9;
    @Config.Comment("Fish Spawn Max Amount. [Default: 8]")
    public static int Fish_Spawn_MAX_Amount = 8;
    @Config.Comment("Shark Spawn Max Amount. [Default: 2]")
    public static int Shark_Spawn_MAX_Amount = 2;
    @Config.Comment("Eel Spawn Max Amount. [Default: 4]")
    public static int Eel_Spawn_MAX_Amount = 4;
    
    @Config.Comment("Coconut Drink Yield. [Default: 10]")
    public static int Coconut_Drink_Yield = 10;
    @Config.Comment("Coconut Hydration Yield. [Default: 6.7F]")
    public static float Coconut_Hydration_Yield = 6.7f;
    
    @Config.Comment("Tin Can Drink Yield. [Default: 8]")
    public static int Tin_Can_Drink_Yield = 8;
    @Config.Comment("Tin Can Hydration Yield. [Default: 1F]")
    public static float Tin_Can_Hydration_Yield = 1f;  
    
    @Config.Comment("Air On Scuba. [Default: 900]")
    public static int Air_On_Scuba = 900;
    @Config.Comment("Damage If Scuba Air Runs Out. [Default: 2]")
    public static int Damage_If_Scuba_Air_Runs_Out = 2;
    
    
    @Config.Comment("Is Scrap Placeable. [Default: true]")
    public static boolean Is_Scrap_Placeable = true;
    @Config.Comment("Is Plank Placeable. [Default: true]")
    public static boolean Is_Plank_Placeable = true;
    @Config.Comment("Is Thatch Placeable. [Default: true]")
    public static boolean Is_Thatch_Placeable = true;
    @Config.Comment("Is Barrel Placeable. [Default: true]")
    public static boolean Is_Barrel_Placeable = true;
}
