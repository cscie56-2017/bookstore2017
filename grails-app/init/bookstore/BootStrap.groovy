package bookstore

import cscie56.demo.Author
import cscie56.demo.Book
import cscie56.demo.Publisher
import cscie56.demo.Role
import cscie56.demo.User
import cscie56.demo.UserRole
import groovy.sql.Sql

import java.util.logging.Level

class BootStrap {

    def init = { servletContext ->
        println "******************* bookstore.BootStrap.groovy!!!! ***************"

        environments {
            development {
                Sql.LOG.level = Level.FINE
                setupData()
                setupUsersAndRoles()
            }
            test {
                setupData()
                setupUsersAndRoles()
            }
            production {
                // do nothing
            }
        }
    }
    def destroy = {
    }

    def setupUsersAndRoles(){
        User admin = new User(username: 'admin',password: 'supersecret')
        saveObject(admin)
        User user = new User(username: 'user', password: 'secret')
        saveObject(user)

        Role adminRole = new Role(authority: Role.ROLE_ADMIN)
        saveObject(adminRole)
        Role userRole = new Role(authority: Role.ROLE_USER)
        saveObject(userRole)

        UserRole.create(admin,adminRole)
        UserRole.create(admin,userRole)
        UserRole.create(user,userRole)
    }

    def setupData(){
        Publisher p1 = new Publisher(name: 'Doubleday', dateEstablished: Date.parse("yyyy", '1897'), type: 'Trade')
        saveObject(p1)

        Publisher p2 = new Publisher(name: 'Collins', dateEstablished: Date.parse("yyyy", '1819'), type: 'Trade')
        saveObject(p2)

        Publisher p3 = new Publisher(name: 'Viking Press', dateEstablished: Date.parse("yyyy", '1925'), type: 'Trade')
        saveObject(p3)

        Publisher p4 = new Publisher(name:'Random House', dateEstablished: Date.parse("yyyy", '1925'), type: 'Trade')

        Author a1 = new Author(firstName: 'Stephen', lastName: 'King', birthDate: Date.parse("MM/dd/yyyy", "3/2/1943"))
        saveObject(a1)

        Author a3 = new Author(firstName: 'Peter', lastName: 'Straub', birthDate: Date.parse("MM/dd/yyyy", "9/21/1947"))
        saveObject(a3)

        saveBook('The Shining', "1/27/1977", [a1], '978-0-385-12167-5', p1, 1999, 'Horror',"Jack Torrance’s new job at the Overlook Hotel is the perfect chance for a fresh start. As the off-season caretaker at the atmospheric old hotel, he’ll have plenty of time to spend reconnecting with his family and working on his writing. But as the harsh winter weather sets in, the idyllic location feels ever more remote . . . and more sinister. And the only one to notice the strange and terrible forces gathering around the Overlook is Danny Torrance, a uniquely gifted five-year-old.")
        saveBook("Salem's Lot", "10/17/1975", [a1], '978-0-385-00751-1', p1, 2499, 'Horror',"Ben Mears has returned to Jerusalem's Lot in the hopes that living in an old mansion, long the subject of town lore, will help him cast out his own devils and provide inspiration for his new book. But when two young boys venture into the woods and only one comes out alive, Mears begins to realize that there may be something sinister at work and that his hometown is under siege by forces of darkness far beyond his control.")
        saveBook("Carrie", "4/5/1974", [a1], '978-0-385-08695-0', p1, 2999, 'Horror',"Carrie White may be picked on by her classmates, but she has a gift. She can move things with her mind. Doors lock. Candles fall. This is her power and her problem. Then, an act of kindness, as spontaneous as the vicious taunts of her classmates, offers Carrie a chance to be a normal...until an unexpected cruelty turns her gift into a weapon of horror and destruction that no one will ever forget.")
        saveBook("The Dead Zone", "8/1/1979", [a1], '978-0-670-26077-5', p3, 2999, 'Horror',"When Johnny Smith was six-years-old, head trauma caused by a bad ice-skating accident left him with a nasty bruise on his forehead and, from time to time, those hunches…infrequent but accurate snippets of things to come. But it isn’t until Johnny’s a grown man—now having survived a horrifying auto injury that plunged him into a coma lasting four-and-a-half years—that his special abilities really push to the fore. Johnny Smith comes back from the void with an extraordinary gift that becomes his life’s curse…presenting visions of what was and what will be for the innocent and guilty alike. But when he encounters a ruthlessly ambitious and amoral man who promises a terrifying fate for all humanity, Johnny must find a way to prevent a harrowing predestination from becoming reality.")
        saveBook("Firestarter", "9/1/1980", [a1],   '978-9-754-05392-0', p3, 2999, 'Horror',"<p>First, a man and a woman are subjects of a top-secret government experiment designed to produce extraordinary psychic powers.</p>" +
                "<p>Then, they are married and have a child. A daughter.</p>" +
                "<p>Early on the daughter shows signs of a wild and horrifying force growing within her. Desperately, her parents try to train her to keep that force in check, to “act normal.”</p>" +
                "<p>Now the government wants its brainchild back—for its own insane ends.</p>")
        saveBook("Cujo", "9/1/1981", [a1],   '978-8-385-85539-2', p3, 2999, 'Horror',"Cujo used to be a big friendly dog, lovable and loyal to his trinity (THE MAN, THE WOMAN, and THE BOY) and everyone around him, and always did his best to not be a BAD DOG. But that all ends on the day this nearly two-hundred-pound Saint Bernard makes the mistake of chasing a rabbit into a hidden underground cave, setting off a tragic chain of events. Now Cujo is no longer himself as he is slowly overcome by a growing sickness, one that consumes his mind even as his once affable thoughts turn uncontrollably and inexorably to hatred and murder. Cujo is about to become the center of a horrifying vortex that will inescapably draw in everyone around him—a relentless reign of terror, fury, and madness from which no one in Castle Rock will truly be safe....")
        saveBook("The Talisman", "11/8/1984", [a1,a3],   '978-0-670-69199-9', p3, 2999, 'Horror',"Why had twelve-year-old Jack Sawyer’s mother frantically moved the two of them from Rodeo Drive to a New York City apartment to the Alhambra, a fading ocean resort and shuttered amusement park in New Hampshire? Who or what is she running from? She is dying . . . and even young Jack knows she can’t outrun death. But only he can save her—for he has been chosen to search for a prize across an epic landscape of dangers and lies, a realm of innocents and monsters, where everything Jack loves is on the line.")

        Author a2 = new Author(firstName: 'Patrick', lastName: "O'Brian", birthDate: Date.parse("MM/dd/yyyy", "12/12/1914"))
        saveObject(a2)

        saveBook('Master and Commander', "1/1/1969", [a2], '0-00-221526-8', p2, 999, 'Drama',"This, the first in the splendid series of Jack Aubrey novels, establishes the friendship between Captain Aubrey, R.N., and Stephen Maturin, ship's surgeon and intelligence agent, against a thrilling backdrop of the Napoleonic wars. Details of a life aboard a man-of-war in Nelson's navy are faultlessly rendered: the conversational idiom of the officers in the ward room and the men on the lower deck, the food, the floggings, the mysteries of the wind and the rigging, and the roar of broadsides as the great ships close in battle.")
        saveBook("Post Captain", "1/1/1972", [a2], '0-00-221657-4', p2, 1299, 'Drama',"\"We've beat them before and we'll beat them again.\" In 1803 Napoleon smashes the Peace of Amiens, and Captain Jack Aubrey, R. N., taking refuge in France from his creditors, is interned. He escapes from France, from debtors' prison, and from a possible mutiny, and pursues his quarry straight into the mouth of a French-held harbor.")
        saveBook("HMS Surprise", "1/1/1973", [a2], '0-397-00998-4', p2, 1999, 'Drama',"Third in the series of Aubrey-Maturin adventures, this book is set among the strange sights and smells of the Indian subcontinent, and in the distant waters ploughed by the ships of the East India Company. Aubrey is on the defensive, pitting wits and seamanship against an enemy enjoying overwhelming local superiority. But somewhere in the Indian Ocean lies the prize that could make him rich beyond his wildest dreams: the ships sent by Napoleon to attack the China Fleet...")
        saveBook("The Mauritius Command", "1/1/1977", [a2], '0-00-222383-X', p2, 1999, 'Drama',"Captain Jack Aubrey is ashore on half pay without a command―until Stephen Maturin arrives with secret orders for Aubrey to take a frigate to the Cape of Good Hope under a commodore's pennant, there to mount an expedition against the French-held islands of Mauritius and La Réunion. But the difficulties of carrying out his orders are compounded by two of his own captains―Lord Clonfert, a pleasure-seeking dilettante, and Captain Corbett, whose severity pushes his crew to the verge of mutiny.")
        saveBook("Desolation Island", "1/1/1978", [a2], '0-00-222145-4', p2, 1999, 'Drama',"Commissioned to rescue Governor Bligh of Bounty fame, Captain Jack Aubrey and his friend and surgeon Stephen Maturin sail the Leopard to Australia with a hold full of convicts. Among them is a beautiful and dangerous spy―and a treacherous disease that decimates the crew. With a Dutch man-of-war to windward, the undermanned, outgunned Leopard sails for her life into the freezing waters of the Antarctic, where, in mountain seas, the Dutchman closes...")
        saveBook("The Fortune of War", "1/1/1979", [a2], '0-00-222498-4', p2, 1999, 'Drama',"Captain Jack Aubrey, R. N., arrives in the Dutch East Indies to find himself appointed to the command of the fastest and best-armed frigate in the Navy. He and his friend Stephen Maturin take passage for England in a dispatch vessel. But the War of 1812 breaks out while they are en route. Bloody actions precipitate them both into new and unexpected scenes where Stephen's past activities as a secret agent return on him with a vengeance.")

        Author a4 = new Author(firstName: 'Alan', lastName: "Furst", birthDate: Date.parse("MM/dd/yyyy", "2/20/1941"))
        saveObject(a4)

        saveBook('Night Soldiers',"1/1/1988",[a4],'978-0375760006',p3, 999,'Historical Fiction',"Bulgaria, 1934. A young man is murdered by the local fascists. His brother, Khristo Stoianev, is recruited into the NKVD, the Soviet secret intelligence service, and sent to Spain to serve in its civil war. Warned that he is about to become a victim of Stalin’s purges, Khristo flees to Paris. Night Soldiers masterfully re-creates the European world of 1934–45: the struggle between Nazi Germany and Soviet Russia for Eastern Europe, the last desperate gaiety of the beau monde in 1937 Paris, and guerrilla operations with the French underground in 1944. Night Soldiers is a scrupulously researched panoramic novel, a work on a grand scale.")
        saveBook('Dark Star',"1/1/1991",[a4],'978-0375759994',p3, 999,'Historical Fiction',"Paris, Moscow, Berlin, and Prague, 1937. In the back alleys of nighttime Europe, war is already under way. André Szara, survivor of the Polish pogroms and the Russian civil wars and a foreign correspondent for Pravda, is co-opted by the NKVD, the Soviet secret intelligence service, and becomes a full-time spymaster in Paris. As deputy director of a Paris network, Szara finds his own star rising when he recruits an agent in Berlin who can supply crucial information. Dark Star captures not only the intrigue and danger of clandestine life but the day-to-day reality of what Soviet operatives call special work.")
        saveBook('The Polish Officer',"1/1/1995",[a4],'067941312X',p3, 999,'Historical Fiction',"In September 1939, as Warsaw falls to the Wehrmacht, Captain Alexander de Milja is recruited to Poland's newly formed underground army, the Związek Walki Zbrojnej (ZWZ), or the Union of Armed Struggle. His first mission is to smuggle the national gold reserves out of the country by means of a refugee train to Bucharest. Under a series of aliases, De Milja undertakes various missions to sabotage German operations. These see him collude with fellow saboteurs in the back alleys and black-market bistros of Paris, working with the underground in the tenements of Warsaw, assisting the British attack on German naval targets in the harbor of Calais, and teaming with partisan guerrillas in the frozen forests of the Ukraine.")
        saveBook('The World at Night',"1/1/1996",[a4],'0-375-75858-5',p3, 999,'Historical Fiction',"The story takes place in and around Paris between May 1940 and June 1941. Jean Casson is a French motion-picture producer who specializes in gangster films and who possesses no political views to speak of. When the Germans defeat and conquer his country, Casson at first tries to continue his life and career as if nothing had happened. But that proves impossible; when the Germans arrest a few of his friends and associates Casson finds himself helping others to hide or escape. He is seen talking to questionable people, and before long his line is tapped and his movements followed. Eventually Casson must choose between a life of resistance or no life at all.")
        saveBook('Red Gold',"1/1/1999",[a4],'978-0375758591',p3, 999,'Historical Fiction',"Autumn 1941: In a shabby hotel off the place Clichy, the course of the war is about to change. German tanks are rolling toward Moscow. Stalin has issued a decree: All partisan operatives are to strike behind enemy lines—from Kiev to Brittany. Set in the back streets of Paris and deep in occupied France, Red Gold moves with quiet menace as predators from the dark edge of war—arms dealers, lawyers, spies, and assassins—emerge from the shadows of the Parisian underworld. In their midst is Jean Casson, once a well-to-do film producer, now a target of the Gestapo living on a few francs a day. As the occupation tightens, Casson is drawn into an ill-fated mission: running guns to combat units of the French Communist Party. Reprisals are brutal. At last the real resistance has begun. Red Gold masterfully re-creates the shadow world of French resistance in the darkest days of World War II.")
    }


    def saveBook(title,dateString,authors,isbn,publisher,price,genre,description) {
        Book b = new Book(title:title,
                          dateOfPublication: Date.parse("MM/dd/yyyy",dateString),
                          authors:authors,
                          isbn:isbn,
                          publisher: publisher,
                          price:price,
                          genre: genre,
                          description:description)
        saveObject(b)
        authors.each { author ->
            author.addToBooks(b)
            author.save()
        }
    }

    def saveObject(object) {
        if (!object.save(flush:true)) {
            object.errors.allErrors.each { println it }
        }
    }
}
