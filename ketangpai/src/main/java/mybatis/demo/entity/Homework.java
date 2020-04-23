package mybatis.demo.entity;

public class Homework {
	private int id;
	private String courseId;
	private String title;
	private String content;
	private String deadline;
	private String score;
	private String file;
	private String createTime;
	private boolean canDelay;
	private String unRead;
	private String read;
	private String unSubmit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public boolean isCanDelay() {
		return canDelay;
	}
	public void setCanDelay(boolean canDelay) {
		this.canDelay = canDelay;
	}
	public String getUnRead() {
		return unRead;
	}
	public void setUnRead(String unRead) {
		this.unRead = unRead;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getUnSubmit() {
		return unSubmit;
	}
	public void setUnSubmit(String unSubmit) {
		this.unSubmit = unSubmit;
	}


	
}
