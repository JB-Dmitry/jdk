/*
 * Copyright 2021 JetBrains s.r.o.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 1111111
 * @summary Test the option to disable paired characters matching while splitting text by script
 */

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.io.File;

public class DisablePairedCharsMatching {
    public static void main(String[] args) throws Exception {
        Font font = Font.createFont(Font.TRUETYPE_FONT,
                new File(System.getProperty("test.src", "."), "FontWithLigatures.ttf"));
        FontRenderContext frc = new FontRenderContext(null, false, false);
        String text = "<=a\u0430=>"; // one Latin and one Cyrillic letter 'a'
        GlyphVector gv = font.layoutGlyphVector(frc, text.toCharArray(), 0, text.length(),
                Font.LAYOUT_NO_PAIRED_CHARS_SCRIPT_MATCHING);
        int glyphCount = gv.getNumGlyphs();
        if (glyphCount != 4) {
            // Our test font defines ligatures for '<=' and '=>' character sequences
            // so we expect them to be rendered using a single glyph
            throw new RuntimeException("Unexpected number of glyphs: " + glyphCount);
        }
    }
}