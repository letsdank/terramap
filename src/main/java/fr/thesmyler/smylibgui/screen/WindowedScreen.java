package fr.thesmyler.smylibgui.screen;

import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import fr.thesmyler.smylibgui.Cursors;
import fr.thesmyler.smylibgui.widgets.IWidget;
import net.minecraft.client.gui.GuiScreen;

public class WindowedScreen extends Screen {

	private static final int BORDER_WIDTH = 5;
	private static final int HEADER_WIDTH = 10;
	private Screen subScreen;
	private String windowTitle;
	
	public WindowedScreen(BackgroundType back, Screen subScreen, String title, int z) {
		super(back);
		this.subScreen = subScreen;
		this.windowTitle = title;
		this.z = z;
		this.removeAllWidgets();
		this.addWidget(this.subScreen);
		this.addWidget(new RightBorderBar());
		this.addWidget(new LeftBorderBar());
		this.addWidget(new TopBorderBar());
		this.addWidget(new BottomBorderBar());
		this.addWidget(new UpperLeftCorner());
		this.addWidget(new UpperRightCorner());
		this.addWidget(new LowerLeftCorner());
		this.addWidget(new LowerRightCorner());
		this.addWidget(new TopBar());
	}

	@Override
	public void draw(
			int x,
			int y,
			int mouseX,
			int mouseY,
			boolean screenHovered,
			boolean screenFocused,
			Screen parent) {
		super.draw(x, y, mouseX, mouseY, screenHovered, screenFocused, parent);
	}
	
	
	private void updateSubScreen() {
		this.subScreen.x = BORDER_WIDTH;
		this.subScreen.y = BORDER_WIDTH + HEADER_WIDTH;
		this.subScreen.width = this.width - BORDER_WIDTH * 2;
		this.subScreen.height = this.height - BORDER_WIDTH * 2 - HEADER_WIDTH;
		this.subScreen.initScreen();
	}

	private abstract class BorderWidget implements IWidget {
		
		private boolean lastHovered = false;
		private Cursor cursor = null;
		
		public BorderWidget(Cursor cursor) {
			this.cursor = cursor;
		}
		
		@Override
		public void draw(int x, int y, int mouseX, int mouseY, boolean hovered, boolean focused, Screen parent) {
			GuiScreen.drawRect(x, y, x + this.getWidth(), y + this.getHeight(), 0xB0000000);
			if(this.lastHovered != hovered && !Mouse.isButtonDown(0)) {
				if(hovered) Cursors.trySetCursor(this.cursor);
				else if(Mouse.getNativeCursor() == this.cursor) Cursors.trySetCursor(null);
				this.lastHovered = hovered;
			}
		}
		
	}
	
	private class RightBorderBar extends BorderWidget {
		
		public RightBorderBar() {
			super(Cursors.CURSOR_RESIZE_HORIZONTAL);
		}

		@Override
		public int getX() {
			return WindowedScreen.this.width - BORDER_WIDTH;
		}

		@Override
		public int getY() {
			return BORDER_WIDTH;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return BORDER_WIDTH;
		}

		@Override
		public int getHeight() {
			return WindowedScreen.this.height - BORDER_WIDTH * 2;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.width += dX;
			WindowedScreen.this.updateSubScreen();
		}

	}

	private class LeftBorderBar extends BorderWidget {

		public LeftBorderBar() {
			super(Cursors.CURSOR_RESIZE_HORIZONTAL);
		}

		@Override
		public int getX() {
			return 0;
		}

		@Override
		public int getY() {
			return BORDER_WIDTH;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return BORDER_WIDTH;
		}

		@Override
		public int getHeight() {
			return WindowedScreen.this.height - BORDER_WIDTH * 2;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.width -= dX;
			WindowedScreen.this.x += dX;
			WindowedScreen.this.updateSubScreen();
		}

	}

	private class BottomBorderBar extends BorderWidget {

		public BottomBorderBar() {
			super(Cursors.CURSOR_RESIZE_VERTICAL);
		}

		@Override
		public int getX() {
			return BORDER_WIDTH;
		}

		@Override
		public int getY() {
			return WindowedScreen.this.height - BORDER_WIDTH;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return WindowedScreen.this.width - BORDER_WIDTH * 2;
		}

		@Override
		public int getHeight() {
			return BORDER_WIDTH;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.height += dY;
			WindowedScreen.this.updateSubScreen();
		}

	}

	private class TopBorderBar extends BorderWidget {

		public TopBorderBar() {
			super(Cursors.CURSOR_RESIZE_VERTICAL);
		}

		@Override
		public int getX() {
			return BORDER_WIDTH;
		}

		@Override
		public int getY() {
			return 0;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return WindowedScreen.this.width - BORDER_WIDTH * 2;
		}

		@Override
		public int getHeight() {
			return BORDER_WIDTH;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.height -= dY;
			WindowedScreen.this.y += dY;
			WindowedScreen.this.updateSubScreen();
		}

	}
	
	private class UpperLeftCorner extends BorderWidget {

		public UpperLeftCorner() {
			super(Cursors.CURSOR_RESIZE_DIAGONAL_1);
		}

		@Override
		public int getX() {
			return 0;
		}

		@Override
		public int getY() {
			return 0;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return BORDER_WIDTH;
		}

		@Override
		public int getHeight() {
			return BORDER_WIDTH;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.width -= dX;
			WindowedScreen.this.height -= dY;
			WindowedScreen.this.y += dY;
			WindowedScreen.this.x += dX;
			WindowedScreen.this.updateSubScreen();
		}

	}
	
	private class LowerLeftCorner extends BorderWidget {

		public LowerLeftCorner() {
			super(Cursors.CURSOR_RESIZE_DIAGONAL_2);
		}

		@Override
		public int getX() {
			return 0;
		}

		@Override
		public int getY() {
			return WindowedScreen.this.height - BORDER_WIDTH;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return BORDER_WIDTH;
		}

		@Override
		public int getHeight() {
			return BORDER_WIDTH;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.width -= dX;
			WindowedScreen.this.height += dY;
			WindowedScreen.this.x += dX;
			WindowedScreen.this.updateSubScreen();
		}

	}
	
	private class LowerRightCorner extends BorderWidget {

		public LowerRightCorner() {
			super(Cursors.CURSOR_RESIZE_DIAGONAL_1);
		}

		@Override
		public int getX() {
			return WindowedScreen.this.width - BORDER_WIDTH;
		}

		@Override
		public int getY() {
			return WindowedScreen.this.height - BORDER_WIDTH;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return BORDER_WIDTH;
		}

		@Override
		public int getHeight() {
			return BORDER_WIDTH;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.width += dX;
			WindowedScreen.this.height += dY;
			WindowedScreen.this.updateSubScreen();
		}

	}
	
	private class UpperRightCorner extends BorderWidget {

		public UpperRightCorner() {
			super(Cursors.CURSOR_RESIZE_DIAGONAL_2);
		}

		@Override
		public int getX() {
			return WindowedScreen.this.width - BORDER_WIDTH;
		}

		@Override
		public int getY() {
			return 0;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return BORDER_WIDTH;
		}

		@Override
		public int getHeight() {
			return BORDER_WIDTH;
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.width += dX;
			WindowedScreen.this.height -= dY;
			WindowedScreen.this.y += dY;
			WindowedScreen.this.updateSubScreen();
		}

	}

	private class TopBar extends BorderWidget {

		public TopBar() {
			super(Cursors.CURSOR_MOVE);
		}

		@Override
		public int getX() {
			return BORDER_WIDTH;
		}

		@Override
		public int getY() {
			return BORDER_WIDTH;
		}

		@Override
		public int getZ() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getWidth() {
			return WindowedScreen.this.width - BORDER_WIDTH * 2;
		}

		@Override
		public int getHeight() {
			return HEADER_WIDTH;
		}

		@Override
		public void draw(int x, int y, int mouseX, int mouseY, boolean hovered, boolean focused, Screen parent) {
			super.draw(x, y, mouseX, mouseY, hovered, focused, parent);
			int width = this.getWidth();
			String toDraw = parent.getFont().trimStringToWidth(WindowedScreen.this.windowTitle, this.getWidth());
			parent.getFont().drawCenteredString(x + width / 2, y, toDraw, 0xFFFFFFFF, true);
		}

		@Override
		public void onMouseDragged(int mouseX, int mouseY, int dX, int dY, int mouseButton, Screen parent) {
			if(mouseButton != 0) return;
			WindowedScreen.this.x += dX;
			WindowedScreen.this.y += dY;
		}

	}

	public WindowedScreen setX(int x) {
		this.x = x;
		return this;
	}

	public WindowedScreen setY(int y) {
		this.y = y;
		return this;
	}

	public WindowedScreen setWidth(int w) {
		this.width = w;
		this.updateSubScreen();
		return this;
	}

	public WindowedScreen setHeight(int h) {
		this.height = h;
		this.updateSubScreen();
		return this;
	}

	/**
	 * @return the window's title
	 */
	public String getWindowTitle() {
		return windowTitle;
	}

	/**
	 * @param windowTitle - the window title to set
	 */
	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	
}