package tat.com.eduhub.dto;

import java.util.List;

public class GetLessonDTO {

	private List<LessonDTO> lessonDTOs;

	private LessonDTO lessonDTO;

	private int currentIndex;

	private boolean next;

	private boolean previous;

	public GetLessonDTO() {

	}

	public GetLessonDTO(List<LessonDTO> lessonDTOs, LessonDTO lessonDTO) {
		super();
		this.lessonDTOs = lessonDTOs;
		this.lessonDTO = lessonDTO;
		checkList();
	}

	public List<LessonDTO> getLessonDTOs() {
		return lessonDTOs;
	}

	public void setLessonDTOs(List<LessonDTO> lessonDTOs) {
		this.lessonDTOs = lessonDTOs;
	}

	public LessonDTO getLessonDTO() {
		return lessonDTO;
	}

	public void setLessonDTO(LessonDTO lessonDTO) {
		this.lessonDTO = lessonDTO;
	}

	public int getCurrent() {
		int index = 0;
		for (int i = 0; i < lessonDTOs.size(); i++) {
			if (lessonDTO.getId() == lessonDTOs.get(i).getId()) {
				index = i;
				break;
			}
		}
		return index;
	}

	public void checkList() {

		if (lessonDTOs.size() == 1) {
			setNext(false);
			setPrevious(false);
		} else {
			int current = getCurrent();
			if (current > 0 && current < lessonDTOs.size() -1) {
				setNext(true);
				setPrevious(true);
			} else if (current == 0) {
				setNext(true);
				setPrevious(false);
			} else if (current == lessonDTOs.size() -1) {
				setNext(false);
				setPrevious(true);
			} else {
				setNext(false);
				setPrevious(false);
			}
		}

	}

	public Long getNextIDLesson() {
		Long id = (long) 0;

		if (isNext()) {
			int next = getCurrent() + 1;

			id = lessonDTOs.get(next).getId();

			return id;
		}else {
			return null;
		}
		
	}

	public Long getPreviousIDLesson() {
		Long id = (long) 0;

		if (isPrevious()) {
			int next = getCurrent() - 1;

			id = lessonDTOs.get(next).getId();
			
			return id;
		}else {
			return null;
		}
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrevious() {
		return previous;
	}

	public void setPrevious(boolean previous) {
		this.previous = previous;
	}

}
