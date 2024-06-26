package tabling.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * MouseListener의 메서드들을 디폴트 메서드로 변경 <BR>
 * 이 인터페이스를 구현하면 원하는 메서드만 오버라이드해서 사용가능 <BR>
 * MouseAdapter는 구현 클래스 이기때문에 프레임을 상속 받는 클래스들에서 중복 상속 불가 <BR>
 */
public interface MyMouseListener extends MouseListener {
	@Override
	default void mousePressed(MouseEvent e) {}

	@Override
	default void mouseClicked(MouseEvent e) {}

	@Override
	default void mouseEntered(MouseEvent e) {}

	@Override
	default void mouseExited(MouseEvent e) {}

	@Override
	default void mouseReleased(MouseEvent e) {}
}
