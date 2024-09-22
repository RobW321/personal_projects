# allows for TUI
import curses
from curses import wrapper
#monitors the time of program
import time
import random

# Welcome to the WPM Typing Tester Game. To start simply run the python script "python3 project.py"
# in the terminal. At the beginning of the game, press any key to start playing. From there, you will
# be shown a random message which you must type out wihtout any errors while you words per minute is
# being tracked below. Your goal is to type as fast as possible. Use this game to improve your typing
# speed and track your progress as you play. To exit the game simply double tap the "esc" key on your computer.
# Now type away and have fun!

def start_screen(stdscr):
    # clear screen
    stdscr.clear()

    # adding text on screen with the assigned pair
    stdscr.addstr("Welcome to the WPM TYPING TEST\n")
    stdscr.addstr("Press any key to begin")

    # refreshes screen
    stdscr.refresh()

     # registers user's key strokes
    stdscr.getkey()

def display_text(stdscr, message, current, wpm = 0):
    stdscr.addstr(message)

    # displays the words per minute while user is typing
    stdscr.addstr(10, 30, f"Words Per Minute: {wpm }")

    # overlays color over message while it is being types
    for i, char in enumerate(current):
        # reprsents list of the correctlys spelled characters
        correct_char = message[i]
        text_color =  curses.color_pair(2)

        # changes color to red if typed char does not match correct char
        if char != correct_char: 
            text_color = curses.color_pair(1)
        stdscr.addstr(0, i, char, text_color)

# reads the lines of the text file
def load_text():
    with open("text.txt", "r") as f:
        lines = f.readlines()
        # returns the random line and removes the backslash at the end
        return random.choice(lines).strip()

def wpm_test(stdscr): 
    # the text the user is typing
    message = load_text()

    # list of what the user has typed
    current_text = []

    wpm = 0
    # records starting time
    start_time = time.time()

    # prevents words per minute from being stagnant when user does 
    # not enter key
    stdscr.nodelay(True)

    while True: 

        # represents the elapsed time we have been typing  
        elapsed_time = max(time.time() - start_time, 1)

        # represents equation for words per minute 
        wpm = round(len(current_text) / (elapsed_time / 60) /5)

        # clears screen and displays message 
        stdscr.clear()

        # runs the display_text function
        display_text(stdscr, message, current_text, wpm)
        
        # refreshes screen
        stdscr.refresh()

        # Combines the current_text list into words to check if it 
        # matches the message (if the player completed the game)
        if "".join(current_text) == message:
            stdscr.nodelay(False)
            break
        
        # continues program if exception is thrown due to lack
        # of key input
        try: 
            # gets key from user
            key = stdscr.getkey()
        except: 
            continue

        # exits program if "esc" button is double clicked
        # esc = 27 in ASCII 
        if ord(key) == 27:
            break

        # removes the last letter if backspace is clicked 
        if key in ("KEY_BACKSPACE", '\b', "\x7f"):
            if len(current_text) > 0:
                current_text.pop()
        # ensures that user cannot type out of the bounds of the message
        elif len(current_text) < len(message): 
            # appends whatever user types to the current text
            current_text.append(key)

def main(stdscr): 
    # set color of foreground and background
    curses.init_pair(1, curses.COLOR_RED, curses.COLOR_BLACK)
    curses.init_pair(2, curses.COLOR_GREEN, curses.COLOR_BLACK)
    curses.init_pair(3, curses.COLOR_WHITE, curses.COLOR_BLACK)

    # runs start_screen function
    start_screen(stdscr)

    while True: 
        # runs wpm_test function
        wpm_test(stdscr)

        # displays message to user after they have correctly typed the message
        stdscr.addstr(10 , 5, "Congrats, you have completed the message! Press any key to continue.\n")
        
        key = stdscr.getkey()
        if ord(key) == 27:
            break

wrapper(main) 