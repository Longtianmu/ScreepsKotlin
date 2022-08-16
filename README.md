# Longtianmu‘s Screeps Bot

Based on https://github.com/exaV/screeps-kotlin-types

v2

基于任务列表，配置文件的爬

一些规范：ID默认存在options[Step.XX.name]["Target]

遇到无法使用ID（跨房间移动）的情况

可以使用 X,Y,ROOM的形式存储
Move会进行判断依据ID还是XYROOM进行move