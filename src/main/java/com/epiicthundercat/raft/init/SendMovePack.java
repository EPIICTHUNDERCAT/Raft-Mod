package com.epiicthundercat.raft.init;

import com.epiicthundercat.raft.Raft;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SendMovePack implements IMessage, IMessageHandler<SendMovePack, IMessage> {
	private float windX;
	private float windZ;

	public SendMovePack() {
	}

	public SendMovePack(float windX, float windZ) {
		this.windX = windX;
		this.windZ = windZ;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.windX = buf.readFloat();
		this.windZ = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.windX);
		buf.writeFloat(this.windZ);
	}

	@Override
	public IMessage onMessage(final SendMovePack message, final MessageContext ctx) {

		if (ctx.side == Side.CLIENT) {
			Raft.windX = message.windX;
			Raft.windZ = message.windZ;
		}

		return null;
	}
}
