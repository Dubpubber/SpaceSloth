package com.loneboat.spacesloth.main.content.systems;

import com.badlogic.gdx.Gdx;
import com.loneboat.spacesloth.main.SpaceSloth;
import com.loneboat.spacesloth.main.game.actors.SlothShip;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

/**
 * com.loneboat.spacesloth.main.content.systems
 * Created by Dubpub on 8/30/2015.
 *
 *  This class is solely responsible for handling the player's parts of their ship.
 *
 */
public class PartFactory {

    private SpaceSloth game;

    private SlothShip player;

    //private List<Part> currentPartList;
    private Part[] currentParts;

    private HashMap<String, Part> partList;

    public PartFactory(SpaceSloth game) {
        this.game = game;
        //currentPartList = Collections.synchronizedList(new LinkedList<Part>());
        partList = new HashMap<String, Part>();
    }

    public void setPlayer(SlothShip player) {
        this.player = player;
    }

    public SlothShip getPlayer() {
        return player;
    }

    public int loadedParts = 0;

    public enum PART_TYPE {

        COCKPIT(0), GUNMOUNT(1), HULL(2), THRUSTERS(3), WINGSET(4),
        ARMORY(5), REACTOR(6), REFINERY(7), TRACTORBEAM(8);

        private final int id;
        private PART_TYPE(int id) {
            this.id = id;
        }

        private int getID() {
            return id;
        }
    }

    public void populatePartList() {
        // Add parts [A-F]
        Document dom = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(Gdx.files.internal("Parts/Series.xml").file());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(dom != null) {
            Node parts = dom.getDocumentElement();
            game.getLogger().info("Parent Node: " + parts.getNodeName());
            NodeList partsList = parts.getChildNodes();
            // Get each series
            for(int i = 0; i < partsList.getLength(); i++) {
                Node series = partsList.item(i);

                // If node is a series, get it's children.
                if(series.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList rankList = series.getChildNodes();

                    // Iterate through it's children.
                    for(int a = 0; a < rankList.getLength(); a++) {
                        Node rank = rankList.item(a);

                        // Now check for ranks.
                        if(rank.getNodeType() == Node.ELEMENT_NODE) {
                            NodeList ranksChildren = rank.getChildNodes();

                            // Iterate through its children.
                            for(int b = 0; b < ranksChildren.getLength(); b++) {
                                Node part = ranksChildren.item(b);
                                String name = part.getNodeName().toUpperCase();

                                // Now that we have the part, create the part and add to the list based on it's name.
                                if(!name.equalsIgnoreCase("#text")) {
                                    PART_TYPE type = PART_TYPE.valueOf(name);
                                    NodeList partNodes = part.getChildNodes();
                                    for(int c = 0; c < partNodes.getLength(); c++) {
                                        Node n = partNodes.item(c);
                                        String nodeName = n.getNodeName();
                                        String content = n.getTextContent();
                                        if (partNodes.getLength() > 0 && content.trim().length() > 0) {
                                            switch (type) {
                                                case GUNMOUNT:

                                                    break;
                                                case COCKPIT:
                                                    

                                                    break;
                                                case HULL:

                                                    break;
                                                case WINGSET:

                                                    break;
                                                case THRUSTERS:

                                                    break;
                                                case REACTOR:

                                                    break;
                                                case ARMORY:

                                                    break;
                                                case TRACTORBEAM:

                                                    break;
                                                case REFINERY:

                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
