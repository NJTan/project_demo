package com.example.finalltest;

public class project_detail {
    private int Ino;
    private int Inumber;
    private String Iname;
    private String start_real_time;
    private String end_real_time;
    private String description;
    private  String[] beizu;
    private  String istate;
    private  String[] memberNames;
    private String start_plan_time,end_plan_time,record_time;
    public project_detail(){
        super();
    }
    public project_detail(int ino,String istate ,String start_real_time,String end_real_time,
                          int Inumber,String Iname,String start_plan_time,String end_plan_time,
                          String record_time,String decription,String[] beizu,String[] memberNames){
        super();
        this.Ino=ino;
        this.Iname=Iname;
        this.Inumber=Inumber;
        this.start_plan_time=start_plan_time;
        this.end_plan_time=end_plan_time;
        this.record_time=record_time;
        this.beizu=beizu;
        this.description=decription;
        this.start_real_time=start_real_time;
        this.end_real_time=end_real_time;
        this.istate=istate;
        this.memberNames=memberNames;

    }
    public String[] getMemberNames(){
        return memberNames;
    }

    public void setMemberNames(String[] memberNames) {
        this.memberNames = memberNames;
    }

    public  String getIstate(){
        return istate;
    }
    public void setIstate(String i){
        this.istate=i;
    }
    public String getStart_real_time(){
       return start_real_time;
    }

    public void setStart_real_time(String start_real_time) {
        this.start_real_time = start_real_time;
    }

    public String getEnd_real_time(){
        return end_real_time;
    }

    public void setEnd_real_time(String end_real_time) {
        this.end_real_time = end_real_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getBeizu() {
        return beizu;
    }

    public void setBeizu(String[] beizu) {
        this.beizu = beizu;
    }

    public int getInumber() {
        return Inumber;
    }
    public void setInumber(int inumber){
        this.Inumber=inumber;
    }
    public int getIno(){
        return Ino;
    }
    public void setIno(int ino){
        this.Ino=ino;
    }

    public String getIname(){
        return Iname;
    }
    public  void setIname(String iname){
        this.Iname=iname;
    }
    public String getStart_plan_time(){
        return start_plan_time;
    }
    public void setStart_plan_time(String start_plan_time){
        this.start_plan_time=start_plan_time;
    }
    public String getEnd_plan_time(){
        return end_plan_time;
    }
    public void setEnd_plan_time(String end_plan_time){
        this.end_plan_time=end_plan_time;

    }
    public String getRecord_time(){
        return record_time;
    }
    public void setRecord_time(String record_time){
        this.record_time=record_time;
    }
    @Override
    public String toString() {
        return "project_detail [ino=" + Ino + ", Iname=" + Iname + ", Inumber="
                + Inumber + ", start_plan_time=" + start_plan_time +
                ", end_plan_time=" + end_plan_time + ",record_time="+record_time+
                ",start_real_time="+start_real_time+",end_real_time="+end_real_time+
                ",istate="+istate+",description"+description+",beizu="+beizu+
                "]";
    }
}