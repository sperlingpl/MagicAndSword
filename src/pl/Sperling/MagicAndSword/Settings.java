/*
 * This file is part of Magic And Sword.
 *
 * Magic And Sword is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Magic And Sword is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Magic And Sword; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package pl.Sperling.MagicAndSword;

/**
 * User: pawel
 * Date: 01.12.12
 * Time: 14:05
 */
public class Settings {
    private static Settings ourInstance = new Settings();
    private static boolean Multitouch;

    public static boolean isMultitouch() {
        return Multitouch;
    }

    public static void setMultitouch(boolean multitouch) {
        Settings.Multitouch = multitouch;
    }

    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }
}
