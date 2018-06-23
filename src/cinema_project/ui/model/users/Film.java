package cinema_project.ui.model.users;

import java.util.Date;

public class Film {
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public int get_prodactYear() {
        return _prodactYear;
    }

    public void set_prodactYear(int _prodactYear) {
        this._prodactYear = _prodactYear;
    }

    public int get_duration() {
        return _duration;
    }

    public void set_duration(int _duration) {
        this._duration = _duration;
    }

    public Date get_start() {
        return _start;
    }

    public void set_start(Date _start) {
        this._start = _start;
    }

    public Date get_end() {
        return _end;
    }

    public void set_end(Date _end) {
        this._end = _end;
    }

    public String get_budget() {
        return _budget;
    }

    public void set_budget(String _budget) {
        this._budget = _budget;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    private String _name;
    private String _description;
    private int _prodactYear;
    private int _duration;
    private Date _start;
    private Date _end;
    private String _genre;
    private String _budget;
    private int _age;
    private int[] _actor;

    public int[] get_actor() {
        return _actor;
    }

    public void set_actor(int[] _actor) {
        this._actor = _actor;
    }

    public int[] get_director() {
        return _director;
    }

    public void set_director(int[] _director) {
        this._director = _director;
    }

    private int[] _director;
    public String _image;

    public String getImage() {
        return _image;
    }

    public void setImage(String image) {
        this._image = image;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_genre() {
        return _genre;
    }

    public void set_genre(String _genre) {
        this._genre = _genre;
    }

    public String get_image() {
        return _image;
    }

    public void set_image(String _image) {
        this._image = _image;
    }

    public Film(int id, String name, String description, int prodactYear, int duration, Date start, Date end, String budget, int age, int[] actor, int[] director, String image, String genre){
        _id = id;
        _name = name;
        _description = description;
        _prodactYear = prodactYear;
        _duration = duration;

        _start = start;
        _end = end;
        _budget = budget;
        _age = age;
        _actor = actor;
        _director = director;
        _image = image;
        _genre = genre;
    }

    @Override
    public String toString(){
        return "id:" + _id + " name:" + _name + " prodactYear:" + _prodactYear + " duration:" + _duration + " start:" + _start + " end:" + _end + " budget:" + _budget + " age:" + _age;
    }
}
