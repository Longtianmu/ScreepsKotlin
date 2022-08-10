package net.ltm.screepsbot.profiles.childProfiles

/*
class RoleSweeper() : SweeperProfile(){
    override fun initGenerator(creep: Creep) {}

    override fun loopGenerator(creep: Creep) {
        val tombstone = creep.room.find(FIND_TOMBSTONES)
        val resource = creep.room.find(FIND_DROPPED_RESOURCES)
        val target = if(resource.isNotEmpty()){
            Pair(resource.first().id,resource.first().resourceType.value)
        }else if(tombstone.isNotEmpty()){
            Pair(tombstone.first().id,tombstone.first().store.keys.first().value)
        }else{
            return
        }
        val storages = getUsableContainer(creep.room)
        creep.assignStepOption(Step.PICKUP,"Target",target.first,false)
        creep.assignStepOption(Step.TRANSFER,"Target",storages,false)
        creep.assignStepOption(Step.TRANSFER,"Type",target.second,false)
    }
}*/