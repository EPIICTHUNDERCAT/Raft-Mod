package com.epiicthundercat.raft;

import net.minecraftforge.common.config.Config;

@Config(modid = Reference.ID)
public class ConfigHandler {

	@Config.Name("Raft")
	public static RaftConfig raft = new RaftConfig();
	
}
